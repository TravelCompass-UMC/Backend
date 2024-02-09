package com.travelcompass.api.location.service;

import com.amazonaws.util.IOUtils;
import com.travelcompass.api.address.service.AddressService;
import com.travelcompass.api.address.service.Coordinates;
import com.travelcompass.api.global.entity.Uuid;
import com.travelcompass.api.global.repository.UuidRepository;
import com.travelcompass.api.global.s3.AmazonS3Manager;
import com.travelcompass.api.location.domain.CustomMultipartFile;
import com.travelcompass.api.location.domain.DayType;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationInfo;
import com.travelcompass.api.location.dto.BusinessHoursDto;
import com.travelcompass.api.location.dto.BusinessHoursDto.CreateBusinessHoursDto;
import com.travelcompass.api.location.dto.LocationScrapingDto;
import com.travelcompass.api.location.repository.LocationRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LocationScraper {

    private static final String BASE_URL = "https://pcmap.place.naver.com/place/";
    private static final String HOME_TAB = "/home";
    private static final String PHOTO_TAB = "/photo";

    private final UuidRepository uuidRepository;
    private final LocationRepository locationRepository;

    private final AmazonS3Manager s3Manager;
    private final LocationInfoService locationInfoService;
    private final LocationImageService locationImageService;
    private final BusinessHoursService businessHoursService;
    private final AddressService addressService;

    // selenium driver 설정
    private WebDriver driver;

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    public void scrapeLocations() {
        setUp();

        List<LocationInfo> locationInfos = locationInfoService.findAllLocationInfo();
        for (LocationInfo locationInfo : locationInfos) {
            LocationScrapingDto locationScrapingDto = scrapeLocation(locationInfo);

            // 주소를 통해 좌표를 가져옴
            Coordinates coordinate = addressService.getCoordinate(locationScrapingDto.getAddress());

            Location location = Location.builder()
                    .name(locationInfo.getLogicalName())
                    .star(locationScrapingDto.getStar())
                    .roadNameAddress(locationScrapingDto.getAddress())
                    .tel(locationScrapingDto.getTel())
                    .region(locationInfo.getRegion())
                    .latitude(Double.parseDouble(coordinate.getLatitude()))
                    .longitude(Double.parseDouble(coordinate.getLongitude()))
                    .build();

            Location savedLocation = locationRepository.save(location);

            locationImageService.saveImageUrl(locationScrapingDto.getImageUrl(), savedLocation);
            businessHoursService.save(locationScrapingDto.getBusinessHours(),
                    savedLocation);
        }

        tearDown();
    }

    private void setUp() {
        // Chrome driver setup
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless");
        options.addArguments("window-size=1920x1080");
        options.addArguments("disable-gpu");
        options.addArguments("--remote-allow-origin=*");

        driver = new ChromeDriver(options);
    }

    private LocationScrapingDto scrapeLocation(LocationInfo locationInfo) {
        /*
            `홈` 탭으로 이동
         */
        driver.get(BASE_URL + locationInfo.getScrapingId() + HOME_TAB);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        // 별점
        double star = scrapeStar();

        // 주소
        String address = scrapeAddress();

        // 운영 시간
        WebElement businessHoursElement = driver.findElement(
                By.xpath("//div[contains(@class, 'O8qbU pSavy')]/div"));
        businessHoursElement.click();
        Map<DayType, BusinessHoursDto.CreateBusinessHoursDto> businessHoursDtoMap = scrapeBusinessHours(
                businessHoursElement);

        // 전화 번호
        String tel = scrapeTel();

        /*
            `사진` 탭으로 이동
         */
        driver.get(BASE_URL + locationInfo.getScrapingId() + PHOTO_TAB);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        // 사진
        String src = scrapePhotoSrc();

        MultipartFile image = downloadImage(src);

        Uuid uuid = uuidRepository.save(Uuid.generateUuid());
        String uploadedImageUrl = s3Manager.uploadFile(s3Manager.generateLocationKeyName(uuid), image);

        return LocationScrapingDto.builder()
                .star(star)
                .address(address)
                .businessHours(businessHoursDtoMap)
                .tel(tel)
                .imageUrl(uploadedImageUrl)
                .build();
    }

    private double scrapeStar() {
        String starString = "0.0";
        try {
            starString = driver.findElement(By.xpath(
                            "//div[@class='place_section no_margin OP4V8']/div[1]/div[2]/span[@class='PXMot LXIwF']"))
                    .getText()
                    .split("\n")[1];
        } catch (NoSuchElementException ignored) {
        }

        return Double.parseDouble(starString);
    }

    private String scrapeAddress() {
        return driver.findElement(By.xpath("//div[@class='O8qbU tQY7D']"))
                .findElement(By.xpath("div/a/span[1]")).getText();
    }

    private Map<DayType, BusinessHoursDto.CreateBusinessHoursDto> scrapeBusinessHours(WebElement element) {
        List<WebElement> businessHoursElements = element.findElements(
                By.xpath("//div[@class='w9QyJ']/div/*[@class='A_cdD']"));

        // TODO : TEST
        System.out.println("엘리먼트 개수 : " + businessHoursElements.size());

        Map<DayType, BusinessHoursDto.CreateBusinessHoursDto> businessHoursDtoMap = new EnumMap<>(DayType.class);
        if (businessHoursElements.isEmpty()) {
            log.info("운영 시간 정보가 없습니다.");
            return businessHoursDtoMap;
        }
        // "매일"과 같은 형태로 적혀있는 경우 모든 요일에 대해 같은 운영 시간이 적혀있는 것으로 간주
        else if (businessHoursElements.size() == 1) {
            for (DayType dayType : DayType.values()) {
                businessHoursDtoMap.put(dayType,
                        CreateBusinessHoursDto.builder()
                                .time(businessHoursElements.get(0).getText())
                                .build());
            }
        }
        // "월", "화" 등의 요일로 적혀있는 경우
        else {
            for (WebElement e : businessHoursElements) {
                String weekString = e.findElement(By.xpath("*[1]")).getText();
                String businessHours = e.findElement(By.xpath("*[2]")).getText();

                businessHoursDtoMap.put(DayType.getWeek(weekString),
                        CreateBusinessHoursDto.builder()
                                .time(businessHours)
                                .build());
            }
        }

        // TODO: print all items in businessHoursDtoMap
        System.out.println("운영 시간 테스트 출력");
        for (Map.Entry<DayType, BusinessHoursDto.CreateBusinessHoursDto> entry : businessHoursDtoMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue().getTime());
        }

        return businessHoursDtoMap;
    }

    private String scrapeTel() {
        return driver.findElement(By.xpath("//div[@class='O8qbU nbXkr']/div/span[1]")).getText();
    }

    /*
        개발 및 유지보수가 너무 까다로운 관계로 리뷰 스크래핑은 보류
     */
//    private List<ReviewDto> scrapeReviews() {
//        driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
//
//        boolean elementExists = true;
//        while (elementExists) {
//            try {
//                driver.findElement(By.xpath("//div[@class='place_section k5tcc']/div[2]/div/a"))
//                        .click();
//                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
//            } catch (Exception ex) {
//                elementExists = false;
//            }
//        }
//
//        return driver.findElements(
//                        By.xpath("//div[@class='place_section_content']/ul/li"))
//                .stream()
//                .map(e -> {
//                    String username = e.findElement(By.xpath("div[@class='SdWYt']/a[2]/div[1]"))
//                            .getText();
//
//                    String content = "";
//                    try {
//                        content = e.findElement(By.xpath("div[@class='ZZ4OK IwhtZ']/a/span"))
//                                .getText();
//                    } catch (NoSuchElementException ex) {
//                        log.error("리뷰 내용이 없습니다.", ex);
//                    }
//
//                    String reviewDate = e.findElement(By.xpath("div[@class='qM6I7']/div/div[2]/span[1]/time"))
//                            .getText();
//
//                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
//
//                    return ReviewDto.builder()
//                            .username(username)
//                            .content(content)
//                            .reviewDate(reviewDate)
//                            .build();
//                })
//                .toList();
//    }

    private String scrapePhotoSrc() {
        return driver.findElement(
                        By.xpath("//div[@class='place_section_content']/div/div/div[1]/a/img"))
                .getAttribute("src");
    }

    private MultipartFile downloadImage(String src) {
        try (InputStream in = new URL(src).openStream()) {
            return new CustomMultipartFile(IOUtils.toByteArray(in));
        } catch (Exception ex) {
            log.error("이미지 다운로드에 실패했습니다.", ex);
        }
        return null;
    }

    private void tearDown() {
        driver.quit();
    }

}
