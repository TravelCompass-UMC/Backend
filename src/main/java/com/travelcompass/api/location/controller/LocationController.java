package com.travelcompass.api.location.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.location.converter.LocationConverter;
import com.travelcompass.api.location.domain.BusinessHours;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationInfo;
import com.travelcompass.api.location.dto.LocationResponseDto.DetailLocationDto;
import com.travelcompass.api.location.service.BusinessHoursService;
import com.travelcompass.api.location.service.LocationImageService;
import com.travelcompass.api.location.service.LocationInfoService;
import com.travelcompass.api.location.service.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationInfoService locationInfoService;
    private final LocationImageService locationImageService;
    private final BusinessHoursService businessHoursService;

    @GetMapping("/{locationId}")
    public ApiResponse<DetailLocationDto> getLocationDetail(@PathVariable Long locationId) {
        Location location = locationService.findById(locationId);
        LocationInfo locationInfo = locationInfoService.findLocationInfoByLocationId(locationId);
        String imageUrl = locationImageService.findImageUrlByLocationId(locationId);
        List<BusinessHours> businessHoursList = businessHoursService.findListByLocationId(locationId);

        return ApiResponse.onSuccess(
                LocationConverter.toDetailLocationDto(location, locationInfo, imageUrl, businessHoursList));
    }

    @GetMapping("/regions/{regionId}")
    public ApiResponse<List<DetailLocationDto>> getLocationsByRegion(@PathVariable Long regionId) {
        List<Location> findLocations = locationService.findListByRegionId(regionId);
        return ApiResponse.onSuccess(
                findLocations.stream()
                        .map(location -> {
                            LocationInfo locationInfo = locationInfoService.findLocationInfoByLocationId(
                                    location.getId());
                            String imageUrl = locationImageService.findImageUrlByLocationId(location.getId());
                            List<BusinessHours> businessHoursList = businessHoursService.findListByLocationId(
                                    location.getId());

                            return LocationConverter.toDetailLocationDto(location, locationInfo, imageUrl,
                                    businessHoursList);
                        })
                        .toList());
    }

}
