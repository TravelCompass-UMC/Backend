package com.travelcompass.api.location.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.service.LocationLikeService;
import com.travelcompass.api.location.service.LocationService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.oauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "장소 좋아요", description = "장소 좋아요 관련 api 입니다. - 양지원")
@RestController
@RequestMapping("/locations/{location-id}")
@RequiredArgsConstructor
public class LocationLikeController {
    private final UserService userService;
    private final LocationService locationService;
    private final LocationLikeService locationLikeService;

    @Operation(summary = "장소 좋아요 메서드", description = "장소를 좋아요하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LOCATION_2021", description = "장소 좋아요 성공")
    })
    @PostMapping("/like")
    public ApiResponse<Long> toggleLike(@PathVariable(name = "location-id") Long locationId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Location location = locationService.findById(locationId);

        if (location == null) {
            throw GeneralException.of(ErrorCode.LOCATION_NOT_FOUND);
        }

        // 좋아요 토글 및 좋아요 수 조회
        Location updatedLocation = locationLikeService.toggleLikeAndRetrieveCount(location, user);

        return ApiResponse.onSuccess(SuccessCode.LOCATION_LIKE_SUCCESS, updatedLocation.getLikeCount());
    }

    @Operation(summary = "장소 좋아요 취소 메서드", description = "장소 좋아요를 취소하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LOCATION_2022", description = "장소 좋아요 취소 성공")
    })
    @DeleteMapping("/like")
    public ApiResponse<Long> cancelLike(@PathVariable(name = "location-id") Long locationId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Location location = locationService.findById(locationId);

        if (location == null) {
            throw GeneralException.of(ErrorCode.LOCATION_NOT_FOUND);
        }

        // 좋아요 취소 및 좋아요 수 조회
        Location updatedLocation = locationLikeService.cancelLikeAndRetrieveCount(location, user);

        return ApiResponse.onSuccess(SuccessCode.LOCATION_UNLIKE_SUCCESS, updatedLocation.getLikeCount());
    }

    @Operation(summary = "장소 좋아요 개수 조회 메서드", description = "장소 좋아요 개수 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LOCATION_2023", description = "장소 좋아요 개수 조회 성공")
    })
    @GetMapping("/like/count")
    public ApiResponse<Long> getLikeCount(@PathVariable(name = "location-id") Long locationId) {
        Location location = locationService.findById(locationId);

        if (location == null) {
            throw GeneralException.of(ErrorCode.LOCATION_NOT_FOUND);
        }

        // 좋아요 수 조회
        Long likeCount = locationLikeService.getLikeCount(location);

        return ApiResponse.onSuccess(SuccessCode.LOCATION_LIKE_COUNT_SUCCESS, likeCount);
    }
}
