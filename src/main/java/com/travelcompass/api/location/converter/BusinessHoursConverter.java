package com.travelcompass.api.location.converter;

import com.travelcompass.api.location.domain.BusinessHours;
import com.travelcompass.api.location.domain.DayType;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.dto.BusinessHoursDto;

public class BusinessHoursConverter {

    private BusinessHoursConverter() {
    }

    public static BusinessHours toBusinessHours(DayType dayType, BusinessHoursDto businessHoursDto,
            Location location) {
        return BusinessHours.builder()
                .dayType(dayType)
                .openTime(businessHoursDto.getOpenTime())
                .closeTime(businessHoursDto.getCloseTime())
                .location(location)
                .build();
    }

}
