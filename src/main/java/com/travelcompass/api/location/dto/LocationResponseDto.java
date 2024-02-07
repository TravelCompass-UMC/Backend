package com.travelcompass.api.location.dto;

import com.travelcompass.api.location.domain.LocationType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LocationResponseDto {

    private LocationResponseDto() {
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailLocationResponseDto {

        private Long id;

        private String name;

        private LocationType locationType;

        private Double star;

        private String roadNameAddress;

        private List<BusinessHoursDto> businessHoursDtos;

        private String tel;

        private String imageUrl;

        private Double latitude;

        private Double longitude;
    }

}
