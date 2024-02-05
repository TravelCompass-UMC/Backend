package com.travelcompass.api.mypage.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.mypage.converter.MypageConverter;
import com.travelcompass.api.mypage.dto.MypageResponseDto;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MyInfoDto;
import com.travelcompass.api.mypage.service.MypageService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.oauth.service.UserService;
import com.travelcompass.api.plan.converter.PlanConverter;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLike;
import com.travelcompass.api.plan.dto.PlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanListResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelcompass.api.plan.dto.PlanResponseDto.*;

@Tag(name = "마이페이지", description = "마이페이지 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/me")
public class MypageController {

    private final MypageService mypageService;
    private final UserService userService;

    // 나의 정보 조회
    @GetMapping("/info")
    public ApiResponse<MyInfoDto> findMyInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.MYPAGE_INFO_VIEW_SUCCESS, MypageConverter.myInfoDto(user));
    }

    // 나의 계획 조회
    @GetMapping("/plans")
    public ApiResponse<PlanListResponseDto> findMyPlans(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "page") Integer page
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        List<Plan> plansByUser = mypageService.findPlansByUser(user);
        Page<Plan> plans = mypageService.convertPlanToPage(plansByUser, page, 12);

        return ApiResponse.onSuccess(SuccessCode.MYPAGE_PLAN_LIST_VIEW_SUCCESS, PlanConverter.planListResponseDto(plans));
    }


}
