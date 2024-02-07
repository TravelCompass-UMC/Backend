package com.travelcompass.api.location.dto;

import com.travelcompass.api.location.domain.DayType;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.NonLeaked;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationScrapingDto {

    Double star;

    String address;

    Map<DayType, BusinessHoursDto.CreateBusinessHoursDto> businessHours;

    String tel;

    String imageUrl;

}
