package com.travelcompass.api.region.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RegionResponseDto {

    private RegionResponseDto() {
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailRegionDto {

        @Schema(description = "지역 id")
        private Long id;

        @Schema(description = "지역명")
        private String name;
    }
}
