package com.travelcompass.api.location.converter;

import com.travelcompass.api.location.domain.BusinessHours;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationInfo;
import com.travelcompass.api.location.dto.LocationResponseDto;
import java.util.List;

public class LocationConverter {

    private LocationConverter() {
    }

    public static LocationResponseDto.DetailLocationDto toDetailLocationDto(
            Location location,
            LocationInfo locationInfo,
            String imageUrl,
            List<BusinessHours> businessHoursList) {

        return LocationResponseDto.DetailLocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .locationType(locationInfo.getLocationType())
                .star(location.getStar())
                .roadNameAddress(location.getRoadNameAddress())
                .businessHoursDtos(businessHoursList.stream()
                        .map(BusinessHoursConverter::toBusinessHoursDto)
                        .toList())
                .tel(location.getTel())
                .imageUrl(imageUrl)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

}
