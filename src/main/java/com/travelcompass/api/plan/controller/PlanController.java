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
import com.travelcompass.api.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.description.type.TypeList;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelcompass.api.plan.dto.PlanRequestDto.*;

@Tag(name = "여행계획", description = "여행 계획 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/plans")
public class PlanController {

    private final UserService userService;
    private final PlanService planService;
    private final HashtagService hashtagService;
    private final RegionService regionService;

    // plan 새로 만들기
    @Operation(summary = "여행 계획 생성 메서드", description = "여행 계획을 생성하는 메서드입니다. - 김현우")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2011", description = "계획 생성 성공")
    })
    @PostMapping ("")
    public ApiResponse<PlanLocationListDto> createNewPlan(
            @RequestBody CreatePlanDto createPlanDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
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
    @Operation(summary = "여행 계획 수정 메서드", description = "여행 계획을 수정하는 메서드입니다. - 김현우")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2002", description = "계획 수정 성공")
    })
    @Parameter(name = "plan-id", description = "여행계획의 아이디, path variable")
    @PatchMapping("{plan-id}")
    public ApiResponse<PlanLocationListDto> modifyMyPlan(
            @PathVariable(name = "plan-id") Long planId,
            @RequestBody CreatePlanDto createPlanDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Plan plan = planService.editPlan(createPlanDto.getPlanReqDto(), planService.findPlanById(planId));
        List<String> hashtags = hashtagService.findHashtagsByPlan(plan).stream().map(Hashtag::getName).toList();
        List<PlanLocation> planLocations = planService.editPlanLocations(createPlanDto.getPlanLocationListDto(), plan);

        return ApiResponse.onSuccess(SuccessCode.PLAN_MODIFIED,
                PlanConverter.planLocationListDto(planLocations, PlanConverter.detailPlanResponseDto(plan, hashtags)));
    }

    // 초대코드로 초대하기
    @Operation(summary = "여행 계획 초대 메서드", description = "여행 계획에 초대하는 메서드입니다. - 김현우")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2012", description = "계획 초대 성공")
    })
    @Parameter(name = "invite-code", description = "여행계획의 초대코드, path variable")
    @PostMapping("/{invite-code}")
    public ApiResponse<DetailPlanResponseDto> inviteUserToPlan(
            @PathVariable(name = "invite-code") String inviteCode,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Plan plan = planService.findPlanByInviteCode(inviteCode);
        planService.createNewPlanUser(plan, user);
        List<Hashtag> hashtags = hashtagService.findHashtagsByPlan(plan);
        List<String> hashtagNames = hashtags.stream().map(Hashtag::getName).collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.PLAN_INVITE_SUCCESS, PlanConverter.detailPlanResponseDto(plan, hashtagNames));
    }

    // 모든 일차 가져오기
    @Operation(summary = "모든일자 조회 메서드", description = "여행 계획의 모든일자의 계획을 조회하는 메서드입니다. - 김현우")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2001", description = "계획 조회 성공")
    })
    @Parameter(name = "plan-id", description = "여행계획의 아이디, path variable")
    @GetMapping("/{plan-id}")
    public ApiResponse<PlanLocationListDto> getAllPlanDetails(
            @PathVariable(name = "plan-id") Long planId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Plan plan = planService.increaseViewCount(planService.findPlanById(planId));
        List<String> hashtags = hashtagService.findHashtagsByPlan(plan).stream().map(Hashtag::getName).toList();
        DetailPlanResponseDto planDto = PlanConverter.detailPlanResponseDto(plan, hashtags);

        List<PlanLocation> planEveryDay = planService.findPlanEveryDay(plan);
        return ApiResponse.onSuccess(SuccessCode.PLAN_VIEW_SUCCESS ,PlanConverter.planLocationListDto(planEveryDay, planDto));
    }

    // day 일차 가져오기
    @Operation(summary = "특정일자 조회 메서드", description = "여행 계획의 특정일자의 계획을 조회하는 메서드입니다. - 김현우")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2001", description = "계획 조회 성공")
    })
    @Parameters({
            @Parameter(name = "plan-id", description = "여행계획의 아이디, path variable"),
            @Parameter(name = "day", description = "조회하고 싶은 날, path variable")
    })
    @GetMapping("/{plan-id}/{day}")
    public ApiResponse<PlanLocationListDto> getDayPlanDetails(
            @PathVariable(name = "plan-id") Long planId,
            @PathVariable(name = "day") Long day,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Plan plan = planService.increaseViewCount(planService.findPlanById(planId));
        List<String> hashtags = hashtagService.findHashtagsByPlan(plan).stream().map(Hashtag::getName).toList();
        DetailPlanResponseDto planDto = PlanConverter.detailPlanResponseDto(plan, hashtags);

        List<PlanLocation> planLocationByDay = planService.findPlanLocationByDay(plan, day);
        return ApiResponse.onSuccess(SuccessCode.PLAN_VIEW_SUCCESS, PlanConverter.planLocationListDto(planLocationByDay, planDto));
    }

    @Operation(summary = "여행계획 조회 메서드", description = "여행 계획 리스트를 조회하는 메서드입니다. - 김현우")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2001", description = "계획 조회 성공")
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 0번이 1페이지 입니다."),
            @Parameter(name = "days", description = "총 여행일수를 숫자로 설정 / 당일치기는 0, 음수를 넣으면 2일 이상인 모든 여행계획을 검색함/ 설정 안하면 모든 여행계획 중 검색"),
            @Parameter(name = "regionId", description = "조회하고 싶은 지역의 아이디 / 설정 안하면 모든 지역의 여행계획 중에서 검색"),
            @Parameter(name = "vehicle", description = "PUBLIC, PRIVATE 중에 설정 / 대중교통, 자가용 / 설정 안하면 모든 여행계획 중 검색"),
            @Parameter(name = "way", description = "정렬 방식 / 1, 2, 3 중 설정 / 1.좋아요순, 2.조회수순, 3.최신순 "),
            @Parameter(name = "hashtags", description = "문자열 리스트 / 해쉬태그가 하나라도 포함되어있는 여행계획을 반환")
    })
    @GetMapping("/search")
    public ApiResponse<PlanListResponseDto> getPlanList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "days", required = false) Integer days,
            @RequestParam(name = "regionId", required = false) Long regionId,
            @RequestParam(name = "vehicle", required = false) String vehicle,
            @RequestParam(name = "way", defaultValue = "3") Integer way,
            @RequestParam(name = "hashtags", required = false) List<String> hashtags
    ){
        Page<Plan> plans = planService.searchPlans(page, 12, way, days, regionId, vehicle, hashtags);
        PlanConverter.planListResponseDto(plans);
        return ApiResponse.onSuccess(SuccessCode.PLAN_VIEW_SUCCESS, PlanConverter.planListResponseDto(plans));
    }

}
