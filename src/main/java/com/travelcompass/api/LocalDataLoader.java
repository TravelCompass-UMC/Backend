package com.travelcompass.api;

import com.travelcompass.api.location.domain.BusinessHours;
import com.travelcompass.api.location.domain.DayType;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationImage;
import com.travelcompass.api.location.domain.LocationInfo;
import com.travelcompass.api.location.domain.LocationType;
import com.travelcompass.api.location.repository.BusinessHoursRepository;
import com.travelcompass.api.location.repository.LocationImageRepository;
import com.travelcompass.api.location.repository.LocationInfoRepository;
import com.travelcompass.api.location.repository.LocationRepository;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.repository.RegionRepository;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class LocalDataLoader implements ApplicationRunner {

    private final RegionRepository regionRepository;
    private final LocationInfoRepository locationInfoRepository;
    private final LocationRepository locationRepository;
    private final BusinessHoursRepository businessHoursRepository;
    private final LocationImageRepository locationImageRepository;

    @Override
    public void run(ApplicationArguments args) {
        // 지역 초기화
        Region region = regionRepository.save(Region.builder()
                .name("서울")
                .build());

        // 장소 초기화
        Location location1 = locationRepository.save(Location.builder()
                .id(1L)
                .name("서울숲")
                .star(4.2)
                .roadNameAddress("서울특별시 성동구 뚝섬로 273")
                .tel("02-460-2905")
                .latitude(37.543072)
                .longitude(127.041808)
                .region(region)
                .build());

        // 장소정보 초기화
        locationInfoRepository.save(LocationInfo.builder()
                .location(location1)
                .logicalName("서울숲")
                .scrapingId("11636039")
                .locationType(LocationType.ATTRACTION)
                .region(region)
                .build());

        // 영업시간 초기화
        businessHoursRepository.save(BusinessHours.builder()
                .location(location1)
                .dayType(DayType.MONDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location1)
                .dayType(DayType.TUESDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location1)
                .dayType(DayType.WEDNESDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location1)
                .dayType(DayType.THURSDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location1)
                .dayType(DayType.FRIDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location1)
                .dayType(DayType.SATURDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location1)
                .dayType(DayType.SUNDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());

        // 이미지 초기화
        locationImageRepository.save(LocationImage.builder()
                .location(location1)
                .url("testUrl1")
                .build());

        // @@@@@@@@@@@@@@@@@@@@@@@@@

        // 장소 초기화
        Location location2 = locationRepository.save(Location.builder()
                .id(2L)
                .name("강남 파이브가이즈")
                .star(4.5)
                .roadNameAddress("서울특별시 성동구 뚝섬로 273")
                .tel("02-460-2905")
                .latitude(37.543072)
                .longitude(127.041808)
                .region(region)
                .build());

        // 장소정보 초기화
        locationInfoRepository.save(LocationInfo.builder()
                .location(location2)
                .logicalName("강남 파이브가이즈")
                .scrapingId("11636039")
                .locationType(LocationType.RESTAURANT)
                .region(region)
                .build());

        // 영업시간 초기화
        businessHoursRepository.save(BusinessHours.builder()
                .location(location2)
                .dayType(DayType.MONDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location2)
                .dayType(DayType.TUESDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location2)
                .dayType(DayType.WEDNESDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location2)
                .dayType(DayType.THURSDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location2)
                .dayType(DayType.FRIDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location2)
                .dayType(DayType.SATURDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());
        businessHoursRepository.save(BusinessHours.builder()
                .location(location2)
                .dayType(DayType.SUNDAY)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(21, 0))
                .build());

        // 이미지 초기화
        locationImageRepository.save(LocationImage.builder()
                .location(location2)
                .url("testUrl2")
                .build());

        // 장소 정보 초기화
//        locationInfoRepository.save(LocationInfo.builder()
//                .logicalName("서울숲")
//                .scrapingId("11636039")
//                .locationType(LocationType.ATTRACTION)
//                .region(region)
//                .build());
//        locationInfoRepository.save(LocationInfo.builder()
//                .logicalName("파이브가이즈 강남")
//                .scrapingId("1898369171")
//                .locationType(LocationType.RESTAURANT)
//                .region(region)
//                .build());
//        locationInfoRepository.save(LocationInfo.builder()
//                .logicalName("서울앵무새")
//                .scrapingId("1240372795")
//                .locationType(LocationType.CAFE)
//                .region(region)
//                .build());
//        locationInfoRepository.save(LocationInfo.builder()
//                .logicalName("나인트리 프리미어 호텔 명동2")
//                .scrapingId("900986994")
//                .locationType(LocationType.ACCOMMODATION)
//                .region(region)
//                .build());
    }

}
