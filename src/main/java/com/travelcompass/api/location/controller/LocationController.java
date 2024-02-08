package com.travelcompass.api.location.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.location.converter.LocationConverter;
import com.travelcompass.api.location.domain.BusinessHours;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationInfo;
import com.travelcompass.api.location.domain.LocationType;
import com.travelcompass.api.location.dto.LocationResponseDto.DetailLocationDto;
import com.travelcompass.api.location.service.BusinessHoursService;
import com.travelcompass.api.location.service.LocationImageService;
import com.travelcompass.api.location.service.LocationInfoService;
import com.travelcompass.api.location.service.LocationLikeService;
import com.travelcompass.api.location.service.LocationService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.oauth.service.UserService;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.service.RegionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationInfoService locationInfoService;
    private final LocationImageService locationImageService;
    private final BusinessHoursService businessHoursService;
    private final LocationLikeService locationLikeService;
    private final UserService userService;
    private final RegionService regionService;

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
    public ApiResponse<List<DetailLocationDto>> getLocationsByRegion(
            @PathVariable Long regionId,
            @RequestParam(name = "type", required = false) LocationType locationType) {
        List<Location> locations;
        if (locationType == null) {
            locations = locationService.findListByRegionId(regionId);
        } else {
            locations = locationService.findListByRegionIdAndLocationType(regionId, locationType);
        }

        return ApiResponse.onSuccess(
                locations.stream()
                        .map(this::convertToDetailLocationDto)
                        .toList());
    }

    @GetMapping("/regions/{regionId}/like")
    public ApiResponse<List<DetailLocationDto>> getLikeLocationsByRegion(
            @PathVariable Long regionId,
            @RequestParam(name = "type", required = false) LocationType locationType,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Region region = regionService.findRegionById(regionId);

        List<Location> locations;
        if (locationType == null) {
            locations = locationLikeService.findLocationsByUser(user, region);
        } else {
            locations = locationLikeService.findLocationsByUserAndLocationType(user, region, locationType);
        }

        return ApiResponse.onSuccess(
                locations.stream()
                        .map(this::convertToDetailLocationDto)
                        .toList());
    }

    @GetMapping("/like")
    public ApiResponse<List<DetailLocationDto>> getLikeLocations(
            @RequestParam(name = "type", required = false) LocationType locationType,
            @RequestParam(name = "sort") String sort,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        User user = userService.findUserByUserName(customUserDetails.getUsername());

        List<Location> locations;
        if (locationType == null) {
            locations = locationLikeService.findLocationsByUser(user, sort);
        } else {
            locations = locationLikeService.findLocationsByUserAndLocationType(user, locationType, sort);
        }

        return ApiResponse.onSuccess(
                locations.stream()
                        .map(this::convertToDetailLocationDto)
                        .toList());
    }

    private DetailLocationDto convertToDetailLocationDto(Location location) {
        LocationInfo locationInfo = locationInfoService.findLocationInfoByLocationId(location.getId());
        String imageUrl = locationImageService.findImageUrlByLocationId(location.getId());
        List<BusinessHours> businessHoursList = businessHoursService.findListByLocationId(location.getId());

        return LocationConverter.toDetailLocationDto(location, locationInfo, imageUrl, businessHoursList);
    }

}
