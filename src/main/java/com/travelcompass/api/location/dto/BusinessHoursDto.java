package com.travelcompass.api.location.dto;

import com.travelcompass.api.location.domain.DayType;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BusinessHoursDto {

    private BusinessHoursDto() {
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateBusinessHoursDto {

        LocalTime openTime;

        LocalTime closeTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessHoursResponseDto {

        DayType dayType;

        LocalTime openTime;

        LocalTime closeTime;
    }


}
