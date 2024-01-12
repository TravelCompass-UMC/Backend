package com.travelcompass.api.region.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegionTest {

    @Test
    @DisplayName("Region 객체가 생성되었는지 확인하는 테스트")
    void createRegion() {
        // given
        Region region = Region.builder()
                .id(1L)
                .name("서울")
                .build();

        // then
        assertEquals(1L, region.getId());
        assertEquals("서울", region.getName());
    }

}
