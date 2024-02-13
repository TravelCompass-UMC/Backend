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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "장소", description = "장소 관련 api 입니다. - 최재영")
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

    @Operation(summary = "장소 상세 조회", description = "장소 상세 정보를 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LOCATION_404", description = "장소를 찾을 수 없습니다.")
    })
    @Parameter(name = "locationId", description = "장소 id", required = true)
    @GetMapping("/{locationId}")
    public ApiResponse<DetailLocationDto> getLocationDetail(@PathVariable Long locationId) {
        Location location = locationService.findById(locationId);
        LocationInfo locationInfo = locationInfoService.findLocationInfoByLocationId(locationId);
        String imageUrl = locationImageService.findImageUrlByLocationId(locationId);
        List<BusinessHours> businessHoursList = businessHoursService.findListByLocationId(locationId);

        return ApiResponse.onSuccess(
                LocationConverter.toDetailLocationDto(location, locationInfo, imageUrl, businessHoursList));
    }

    @Operation(summary = "지역별 장소 리스트 조회", description = "지역별 장소를 리스트로 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LOCATION_404", description = "장소를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REGION_404", description = "지역을 찾을 수 없습니다.")
    })
    @Parameters({
            @Parameter(name = "regionId", description = "지역 id", required = true),
            @Parameter(name = "type", description = "장소 타입", examples = {
                    @ExampleObject(name = "명소", value = "ATTRACTION"),
                    @ExampleObject(name = "식당", value = "RESTAURANT"),
                    @ExampleObject(name = "카페", value = "ACCOMMODATION"),
                    @ExampleObject(name = "숙소", value = "SHOPPING")
            })
    })
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

    @Operation(summary = "지역별 좋아요 장소 리스트 조회", description = "지역별 좋아요 장소를 리스트로 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LOCATION_404", description = "장소를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REGION_404", description = "지역을 찾을 수 없습니다.")
    })
    @Parameters({
            @Parameter(name = "regionId", description = "지역 id", required = true),
            @Parameter(name = "type", description = "장소 타입", examples = {
                    @ExampleObject(name = "명소", value = "ATTRACTION"),
                    @ExampleObject(name = "식당", value = "RESTAURANT"),
                    @ExampleObject(name = "카페", value = "ACCOMMODATION"),
                    @ExampleObject(name = "숙소", value = "SHOPPING")
            })
    })
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

    @Operation(summary = "좋아요 장소 리스트 조회", description = "좋아요 장소를 리스트로 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LOCATION_404", description = "장소를 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "type", description = "장소 타입", examples = {
                    @ExampleObject(name = "명소", value = "ATTRACTION"),
                    @ExampleObject(name = "식당", value = "RESTAURANT"),
                    @ExampleObject(name = "카페", value = "ACCOMMODATION"),
                    @ExampleObject(name = "숙소", value = "SHOPPING")
            }),
            @Parameter(name = "sort", required = true, description = "정렬 기준", examples = {
                    @ExampleObject(name = "좋아요", value = "LIKE"),
                    @ExampleObject(name = "별점", value = "STAR")
            })
    })
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
