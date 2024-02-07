package com.travelcompass.api;

import com.travelcompass.api.location.domain.LocationInfo;
import com.travelcompass.api.location.domain.LocationType;
import com.travelcompass.api.location.repository.LocationInfoRepository;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TempDataLoader implements ApplicationRunner {

    private final RegionRepository regionRepository;
    private final LocationInfoRepository locationInfoRepository;

    @Override
    public void run(ApplicationArguments args) {
        // 지역 초기화
        Region region = regionRepository.save(Region.builder()
                .name("서울")
                .build());

        // 장소 정보 초기화
        locationInfoRepository.save(LocationInfo.builder()
                .logicalName("서울숲")
                .scrapingId("11636039")
                .locationType(LocationType.ATTRACTION)
                .region(region)
                .build());
        locationInfoRepository.save(LocationInfo.builder()
                .logicalName("파이브가이즈 강남")
                .scrapingId("1898369171")
                .locationType(LocationType.RESTAURANT)
                .region(region)
                .build());
        locationInfoRepository.save(LocationInfo.builder()
                .logicalName("서울앵무새")
                .scrapingId("1240372795")
                .locationType(LocationType.CAFE)
                .region(region)
                .build());
        locationInfoRepository.save(LocationInfo.builder()
                .logicalName("나인트리 프리미어 호텔 명동2")
                .scrapingId("900986994")
                .locationType(LocationType.ACCOMMODATION)
                .region(region)
                .build());
    }

}
