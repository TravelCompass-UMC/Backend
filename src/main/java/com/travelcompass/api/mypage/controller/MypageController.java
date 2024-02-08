package com.travelcompass.api.mypage.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.mypage.converter.MypageConverter;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MyInfoDto;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MyLocationListResponseDto;
import com.travelcompass.api.mypage.service.MypageService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.oauth.service.UserService;
import com.travelcompass.api.plan.converter.PlanConverter;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "마이페이지", description = "마이페이지 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/me")
public class MypageController {

    private final MypageService mypageService;
    private final UserService userService;

    // 나의 정보 조회
    @Operation(summary = "나의 정보 조회 메서드", description = "나의 정보를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPAGE_2001", description = "나의 정보 조회 성공")
    })
    @GetMapping("/info")
    public ApiResponse<MyInfoDto> findMyInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.MYPAGE_INFO_VIEW_SUCCESS, MypageConverter.myInfoDto(user));
    }

    // 나의 계획 조회
    @Operation(summary = "나의 여행계획 조회 메서드", description = "나의 여행계획을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPAGE_2002", description = "나의 계획 조회 성공")
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 0번이 1페이지 입니다."),
    })
    @GetMapping("/plans")
    public ApiResponse<PlanListResponseDto> searchMyPlans(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "page") Integer page
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        List<Plan> plansByUser = mypageService.findPlansByUser(user);
        Page<Plan> plans = mypageService.convertPlanToPage(plansByUser, page, 12);

        return ApiResponse.onSuccess(SuccessCode.MYPAGE_PLAN_LIST_VIEW_SUCCESS, PlanConverter.planListResponseDto(plans));
    }

    // 내가 좋아요한 여행계획 조회
    @Operation(summary = "좋아요한 여행계획 조회", description = "내가 좋아요한 여행계획을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPAGE_2003", description = "좋아요한 계획 조회 성공")
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 0번이 1페이지 입니다."),
    })
    @GetMapping("/plans/like")
    public ApiResponse<PlanListResponseDto> searchLikedPlans(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "page") Integer page
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        List<Plan> likedPlans = mypageService.findLikedPlans(user);
        Page<Plan> plans = mypageService.convertPlanToPage(likedPlans, page, 12);

        return ApiResponse.onSuccess(SuccessCode.MYPAGE_LIKED_PLAN_VIEW_SUCCESS, PlanConverter.planListResponseDto(plans));
    }

    // 내가 좋아요한 장소 조회
    @Operation(summary = "좋아요한 장소 조회", description = "내가 좋아요한 장소를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPAGE_2004", description = "좋아요한 장소 조회 성공")
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 0번이 1페이지 입니다."),
            @Parameter(name = "type", description = "조회하고 싶은 장소의 타입, ALL: 전체 조회, ACCOMMODATION: 숙소, RESTAURANT: 식당/카페, ATTRACTION: 명소"),
            @Parameter(name = "way", description = "정렬 방식,  like: 좋아요순, star: 별점순")
    })
    @GetMapping("locations/like")
    public ApiResponse<MyLocationListResponseDto> searchLikedLocations(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "type") String locationType,
            @RequestParam(name = "way") String way
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        List<Location> likedLocations = mypageService.findLikedLocations(user);
        List<Location> filteredLocations = mypageService.filterLocationsByType(likedLocations, locationType);
        List<Location> sortedLocations = mypageService.sortLocationsByWay(filteredLocations, way);
        Page<Location> locations = mypageService.convertLocationToPage(sortedLocations, page, 12);

        return ApiResponse.onSuccess(SuccessCode.MYPAGE_LIKED_LOCATION_VIEW_SUCCESS, MypageConverter.myLocationListResponseDto(locations));
    }
}
