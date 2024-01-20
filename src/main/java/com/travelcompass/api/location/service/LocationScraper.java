package com.travelcompass.api.location.service;

import com.travelcompass.api.location.domain.Week;
import com.travelcompass.api.location.dto.BusinessHoursDto;
import com.travelcompass.api.location.dto.ReviewDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationScraper {

    private static final String url = "https://pcmap.place.naver.com/place/";
    private static final String keyword = "11491438"; // 성산일출봉

    private WebDriver driver;

    public void scrapeLocations() {
        setUp();

        /*
            `홈` 탭으로 이동
         */
        driver.get(url + keyword + "/home");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        // 별점
        double star = scrapeStar();

        // 주소
        String address = scrapeAddress();

        // 운영 시간
        WebElement businessHoursElement = driver.findElement(
                By.xpath("//div[@class='O8qbU pSavy']/div"));
        businessHoursElement.click();
        Map<Week, BusinessHoursDto> businessHoursDtoMap = scrapeBusinessHours(businessHoursElement);

        // 전화 번호
        String tel = scrapeTel();

        /*
            `리뷰` 탭으로 이동
         */
        driver.get(url + keyword + "/review");

        // 리뷰
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        List<ReviewDto> reviewDtos = scrapeReviews();

        /*
            `사진` 탭으로 이동
         */
        driver.get(url + keyword + "/photo");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        // 사진
        String src = scrapePhotoSrc();
        // TODO : S3에 src 업로드

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

    private double scrapeStar() {
        String starString = driver.findElement(
                        By.xpath("//div[@class='place_section no_margin OP4V8']/div[1]/div[2]/span[1]"))
                .getText()
                .split("\n")[1];
        return Double.parseDouble(starString);
    }

    private String scrapeAddress() {
        return driver.findElement(By.xpath("//div[@class='O8qbU tQY7D']"))
                .findElement(By.xpath("div/a/span[1]")).getText();
    }

    private Map<Week, BusinessHoursDto> scrapeBusinessHours(WebElement element) {
        WebElement businessHoursElement = element.findElement(By.xpath("//div/a"));

        List<WebElement> businessHoursElements = businessHoursElement.findElements(
                By.xpath("div[@class='w9QyJ']/div/span"));

        Map<Week, BusinessHoursDto> businessHoursDtoMap = new HashMap<>();
        for (WebElement e : businessHoursElements) {
            String weekString = e.findElement(By.xpath("span")).getText();
            String businessHours = e.findElement(By.xpath("div")).getText();

            businessHoursDtoMap.put(Week.getWeek(weekString), splitBusinessHours(businessHours));
        }

        return businessHoursDtoMap;
    }

    // "07:00 - 19:00"을 LocalTime 2개로 분리
    private BusinessHoursDto splitBusinessHours(String businessHours) {
        String[] split = businessHours.split(" - ");
        return BusinessHoursDto.builder()
                .openTime(LocalTime.parse(split[0]))
                .closeTime(LocalTime.parse(split[1]))
                .build();
    }

    private String scrapeTel() {
        return driver.findElement(By.xpath("//div[@class='O8qbU nbXkr']/div/span[1]"))
                .getText();
    }

    private List<ReviewDto> scrapeReviews() {
        driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);

        try {
            while (true) {
                driver.findElement(By.xpath("//div[@class='place_section k5tcc']/div[2]/div/a"))
                        .click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            }
        } catch (Exception e) {
            // 더 이상 리뷰가 없을 때
        }

        return driver.findElements(
                        By.xpath("//div[@class='place_section_content']/ul/li"))
                .stream()
                .map(e -> {
                    String username = e.findElement(By.xpath("div[1]/a[2]/div[1]"))
                            .getText();
                    String content = e.findElement(By.xpath("div[3]/a/span"))
                            .getText();
                    String visitDate = e.findElement(By.xpath("div[5]/div/div[2]/span[1]/time"))
                            .getText();

                    return ReviewDto.builder()
                            .username(username)
                            .content(content)
                            .visitDate(visitDate)
                            .build();
                })
                .toList();
    }

    private String scrapePhotoSrc() {
        return driver.findElement(
                        By.xpath("//div[@class='place_section_content']/div/div/div[1]/a/img"))
                .getAttribute("src");
    }

    private void tearDown() {
        driver.quit();
    }

}
