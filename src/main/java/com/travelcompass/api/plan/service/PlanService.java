package com.travelcompass.api.plan.service;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.hashtag.service.HashtagService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.plan.converter.PlanConverter;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.domain.PlanUser;
import com.travelcompass.api.plan.domain.ViewCount;
import com.travelcompass.api.plan.dto.PlanRequestDto.CreatePlanDto;
import com.travelcompass.api.plan.repository.PlanLocationRepository;
import com.travelcompass.api.plan.repository.PlanRepository;
import com.travelcompass.api.plan.repository.PlanUserRepository;
import com.travelcompass.api.plan.repository.ViewCountRepository;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final RegionService regionService;
    private final HashtagService hashtagService;
    private final PlanRepository planRepository;
    private final PlanUserRepository planUserRepository;
    private final PlanLocationRepository planLocationRepository;
    private final ViewCountRepository viewCountRepository;

    public Plan createPlan(CreatePlanDto requestDto, User user){
        Region region = regionService.findRegionByName(requestDto.getRegion());
        Plan plan = PlanConverter.toPlan(requestDto, region);
        planRepository.save(plan);

        PlanUser planUser = PlanUser.builder().plan(plan).user(user).build();
        planUserRepository.save(planUser);

        return plan;
    }

    // 해쉬태그를 포함한 여행계획 상세정보 반환
    public Plan findPlanById(Long planId){
        Plan plan = planRepository.findById(planId)
                .orElseThrow( () -> GeneralException.of(ErrorCode.PLAN_NOT_FOUND));

        return plan;
    }

    // 여행 day 일차의 계획 반환
    public List<PlanLocation> findPlanLocationByDay(Long planId, Long day){
        Plan plan = findPlanById(planId);

        return planLocationRepository.findAllByPlanAndTravelDay(plan, day);
    }

    // 여행 모든 일차의 계획 반환
    public List<PlanLocation> findPlanEveryDay(Long planId){

        Plan plan = findPlanById(planId);
        return planLocationRepository.findAllByPlan(plan);
    }

    // 조회수 증가 시키고 조회수 반환
    public Long increaseViewCount(Long planId){
        Plan plan = planRepository.findById(planId)
                .orElseThrow( () -> GeneralException.of(ErrorCode.PLAN_NOT_FOUND));

        viewCountRepository.save(ViewCount.builder().build());

        return viewCountRepository.countAllByPlan(plan);
    }

    // 새로운 PlanUser 만들고 반환하는 메서드 추가
    public PlanUser createNewPlanUser(Plan plan, User user){
        return planUserRepository.save(PlanUser.builder().plan(plan).user(user).build());
    }

}
