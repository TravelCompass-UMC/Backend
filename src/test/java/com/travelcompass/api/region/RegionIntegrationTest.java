//package com.travelcompass.api.region;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import com.travelcompass.api.global.api_payload.ErrorCode;
//import com.travelcompass.api.global.exception.GeneralException;
//import com.travelcompass.api.region.domain.Region;
//import com.travelcompass.api.region.repository.RegionRepository;
//import com.travelcompass.api.region.service.RegionService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//class RegionIntegrationTest {
//
//    @Autowired
//    private RegionRepository regionRepository;
//
//    @Autowired
//    private RegionService regionService;
//
//    @BeforeEach
//    @Transactional
//    void setUp() {
//        Region region1 = Region.builder().id(1L).name("서울").build();
//        regionRepository.save(region1);
//
//        Region region2 = Region.builder().id(2L).name("부산").build();
//        regionRepository.save(region2);
//    }
//
//    @Test
//    @DisplayName("지역 찾기 성공")
//    void 지역_찾기_성공() {
//        // when
//        Region region = regionService.findRegionById(1L);
//
//        // then
//        assertEquals(1L, region.getId());
//        assertEquals("서울", region.getName());
//    }
//
//    @Test
//    @DisplayName("지역 찾기 실패시 REGION_NOT_FOUND 에러 발생")
//    void 지역_찾기_실패() {
//        // when & then
//        GeneralException exception = assertThrows(GeneralException.class,
//                () -> regionService.findRegionById(3L));
//
//        assertEquals(ErrorCode.REGION_NOT_FOUND, exception.getCode());
//    }
//
//
//}
