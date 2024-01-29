package com.travelcompass.api.plan.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.hashtag.domain.Hashtag;
import com.travelcompass.api.hashtag.service.HashtagService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.oauth.service.UserService;
import com.travelcompass.api.plan.converter.PlanConverter;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.dto.PlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.DetailPlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanListResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanLocationListDto;
import com.travelcompass.api.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelcompass.api.plan.dto.PlanRequestDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plans")
public class PlanController {

    private final UserService userService;
    private final PlanService planService;
    private final HashtagService hashtagService;

    // plan 새로 만들기
    @PostMapping ("")
    public ApiResponse<PlanLocationListDto> createNewPlan(
            @RequestBody CreatePlanDto createPlanDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserById(customUserDetails.getId());
        Plan plan = planService.createPlan(createPlanDto.getPlanReqDto(), user);
        List<Hashtag> hashtags = hashtagService.createNewHashtags(createPlanDto.getPlanReqDto().getHashtags());
        hashtagService.createHashtagPlans(hashtags, plan);
        List<String> hashtagNames = hashtags.stream().map(Hashtag::getName).collect(Collectors.toList());

        List<PlanLocation> planLocations = planService.createPlanLocations(createPlanDto.getPlanLocationListDto(), plan);

        return ApiResponse.onSuccess(
                SuccessCode.PLAN_CREATED,
                PlanConverter.planLocationListDto(planLocations, PlanConverter.detailPlanResponseDto(plan, hashtagNames)));
    }

    // plan 수정하기
    @PatchMapping("{plan-id}")
    public ApiResponse<PlanLocationListDto> modifyMyPlan(
            @PathVariable(name = "plan-id") Long planId,
            @RequestBody CreatePlanDto createPlanDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserById(customUserDetails.getId());
        Plan plan = planService.editPlan(createPlanDto.getPlanReqDto(), planService.findPlanById(planId));
        List<String> hashtags = hashtagService.findHashtagsByPlan(plan).stream().map(Hashtag::getName).toList();
        List<PlanLocation> planLocations = planService.editPlanLocations(createPlanDto.getPlanLocationListDto(), plan);

        return ApiResponse.onSuccess(SuccessCode.PLAN_MODIFIED,
                PlanConverter.planLocationListDto(planLocations, PlanConverter.detailPlanResponseDto(plan, hashtags)));
    }

    // 초대코드로 초대하기
    @PostMapping("/{invite-code}")
    public ApiResponse<DetailPlanResponseDto> inviteUserToPlan(
            @PathVariable(name = "invite-code") String inviteCode,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserById(customUserDetails.getId());
        Plan plan = planService.findPlanByInviteCode(inviteCode);
        planService.createNewPlanUser(plan, user);
        List<Hashtag> hashtags = hashtagService.findHashtagsByPlan(plan);
        List<String> hashtagNames = hashtags.stream().map(Hashtag::getName).collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.PLAN_INVITE_SUCCESS, PlanConverter.detailPlanResponseDto(plan, hashtagNames));
    }

    // 모든 일차 가져오기
    @GetMapping("/{plan-id}")
    private ApiResponse<PlanLocationListDto> getAllPlanDetails(
            @PathVariable(name = "plan-id") Long planId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserById(customUserDetails.getId());
        Plan plan = planService.increaseViewCount(planService.findPlanById(planId));
        List<String> hashtags = hashtagService.findHashtagsByPlan(plan).stream().map(Hashtag::getName).toList();
        DetailPlanResponseDto planDto = PlanConverter.detailPlanResponseDto(plan, hashtags);

        List<PlanLocation> planEveryDay = planService.findPlanEveryDay(plan);
        return ApiResponse.onSuccess(SuccessCode.PLAN_VIEW_SUCCESS ,PlanConverter.planLocationListDto(planEveryDay, planDto));
    }

    // day 일차 가져오기
    @GetMapping("/{plan-id}/{day}")
    private ApiResponse<PlanLocationListDto> getDayPlanDetails(
            @PathVariable(name = "plan-id") Long planId,
            @PathVariable(name = "day") Long day,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserById(customUserDetails.getId());
        Plan plan = planService.increaseViewCount(planService.findPlanById(planId));
        List<String> hashtags = hashtagService.findHashtagsByPlan(plan).stream().map(Hashtag::getName).toList();
        DetailPlanResponseDto planDto = PlanConverter.detailPlanResponseDto(plan, hashtags);

        List<PlanLocation> planLocationByDay = planService.findPlanLocationByDay(plan, day);
        return ApiResponse.onSuccess(SuccessCode.PLAN_VIEW_SUCCESS, PlanConverter.planLocationListDto(planLocationByDay, planDto));
    }

    @GetMapping("/search")
    private ApiResponse<PlanListResponseDto> getPlanList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "way") Integer way
    ){
        Page<Plan> planList = planService.getPlanList(page, way);
        return ApiResponse.onSuccess(SuccessCode.PLAN_VIEW_SUCCESS, PlanConverter.planListResponseDto(planList));
    }

}
