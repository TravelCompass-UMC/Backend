package com.travelcompass.api.location.converter;

import com.travelcompass.api.location.domain.BusinessHours;
import com.travelcompass.api.location.domain.DayType;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.dto.BusinessHoursDto;

public class BusinessHoursConverter {

    private BusinessHoursConverter() {
    }

    public static BusinessHours toBusinessHours(DayType dayType, BusinessHoursDto.CreateBusinessHoursDto businessHoursDto,
            Location location) {
        return BusinessHours.builder()
                .dayType(dayType)
                .time(businessHoursDto.getTime())
                .location(location)
                .build();
    }

    public static BusinessHoursDto.BusinessHoursResponseDto toBusinessHoursDto(BusinessHours businessHours) {
        return BusinessHoursDto.BusinessHoursResponseDto.builder()
                .dayType(businessHours.getDayType())
                .time(businessHours.getTime())
                .build();
    }

}
