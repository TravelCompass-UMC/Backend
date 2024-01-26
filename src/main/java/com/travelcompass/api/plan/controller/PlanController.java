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
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanLocationListDto;
import com.travelcompass.api.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping ("")
    public ApiResponse<DetailPlanResponseDto> createNewPlan(
            @RequestBody CreatePlanDto createPlanDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserById(customUserDetails.getId());
        Plan plan = planService.createPlan(createPlanDto, user);
        List<Hashtag> hashtags = hashtagService.createNewHashtags(createPlanDto.getHashtags());
        hashtagService.createHashtagPlans(hashtags, plan);
        List<String> hashtagNames = hashtags.stream().map(Hashtag::getName).collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.PLAN_CREATED, PlanConverter.detailPlanResponseDto(plan, hashtagNames));
    }

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

    @GetMapping("/{plan-id}")
    private ApiResponse<PlanLocationListDto> getAllPlanDetails(
            @PathVariable(name = "plan-id") Long planId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserById(customUserDetails.getId());
        Plan plan = planService.findPlanById(planId);
        List<String> hashtags = hashtagService.findHashtagsByPlan(plan).stream().map(Hashtag::getName).toList();
        DetailPlanResponseDto planDto = PlanConverter.detailPlanResponseDto(plan, hashtags);

        List<PlanLocation> planEveryDay = planService.findPlanEveryDay(plan);
        return ApiResponse.onSuccess(SuccessCode.PLAN_VIEW_SUCCESS ,PlanConverter.planLocationListDto(planEveryDay, planDto));
    }

}
