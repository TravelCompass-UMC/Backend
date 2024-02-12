package com.travelcompass.api.region.converter;

import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.dto.RegionResponseDto;

public class RegionConverter {

    private RegionConverter() {
    }

    public static RegionResponseDto.DetailRegionDto toRegionDto(Region region) {
        return RegionResponseDto.DetailRegionDto.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }

}
