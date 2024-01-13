package com.travelcompass.api.region.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegionTest {

    @Test
    @DisplayName("Region 객체 생성 확인")
    void region_객체_생성_확인() {
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
