package com.travelcompass.api.plan.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.oauth.service.UserService;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.service.PlanLikeService;
import com.travelcompass.api.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@Tag(name = "여행 계획 좋아요", description = "여행 계획 좋아요 관련 api 입니다. - 양지원")
@RestController
@RequestMapping("/plans/{plan-id}")
@RequiredArgsConstructor
public class PlanLikeController {
    private final UserService userService;
    private final PlanService planService;
    private final PlanLikeService planLikeService;

    @Operation(summary = "여행 계획 좋아요 메서드", description = "여행 계획을 좋아요하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2021", description = "계획 좋아요 성공")
    })
    @PostMapping("/like")
    public ApiResponse<Long> toggleLike(@PathVariable(name = "plan-id") Long planId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Plan plan = planService.findPlanById(planId);

        if (plan == null) {
            throw GeneralException.of(ErrorCode.PLAN_NOT_FOUND);
        }

        // 좋아요 토글 및 좋아요 수 조회
        Plan updatedPlan = planLikeService.toggleLikeAndRetrieveCount(plan, user);

        return ApiResponse.onSuccess(SuccessCode.PLAN_LIKE_SUCCESS, updatedPlan.getLikeCount());
    }

    @Operation(summary = "여행 계획 좋아요 취소 메서드", description = "여행 계획을 좋아요를 취소하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2022", description = "계획 좋아요 취소 성공")
    })
    @DeleteMapping("/like")
    public ApiResponse<Long> cancelLike(@PathVariable(name = "plan-id") Long planId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Plan plan = planService.findPlanById(planId);

        if (plan == null) {
            throw GeneralException.of(ErrorCode.PLAN_NOT_FOUND);
        }

        // 좋아요 취소 및 좋아요 수 조회
        Plan updatedPlan = planLikeService.cancelLikeAndRetrieveCount(plan, user);

        return ApiResponse.onSuccess(SuccessCode.PLAN_UNLIKE_SUCCESS, updatedPlan.getLikeCount());
    }

    @Operation(summary = "여행 계획 좋아요 개수 조회 메서드", description = "여행 계획을 좋아요 개수 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2023", description = "계획 좋아요 개수 조회 성공")
    })
    @GetMapping("/like/count")
    public ApiResponse<Long> getLikeCount(@PathVariable(name = "plan-id") Long planId) {
        Plan plan = planService.findPlanById(planId);

        if (plan == null) {
            throw GeneralException.of(ErrorCode.PLAN_NOT_FOUND);
        }

        // 좋아요 수 조회
        Long likeCount = planLikeService.getLikeCount(plan);

        return ApiResponse.onSuccess(SuccessCode.PLAN_LIKE_COUNT_SUCCESS, likeCount);
    }
}
