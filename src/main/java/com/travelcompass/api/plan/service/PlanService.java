package com.travelcompass.api.plan.service;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.service.LocationService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.plan.converter.PlanConverter;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.domain.PlanUser;
import com.travelcompass.api.plan.domain.ViewCount;
import com.travelcompass.api.plan.dto.PlanRequestDto.CreatePlanLocationListDto;
import com.travelcompass.api.plan.dto.PlanRequestDto.PlanReqDto;
import com.travelcompass.api.plan.repository.PlanLocationRepository;
import com.travelcompass.api.plan.repository.PlanRepository;
import com.travelcompass.api.plan.repository.PlanUserRepository;
import com.travelcompass.api.plan.repository.ViewCountRepository;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.service.RegionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final RegionService regionService;
    private final LocationService locationService;
    private final PlanRepository planRepository;
    private final PlanUserRepository planUserRepository;
    private final PlanLocationRepository planLocationRepository;
    private final ViewCountRepository viewCountRepository;

    @Transactional
    public Plan createPlan(PlanReqDto requestDto, User user) {
        Region region = regionService.findRegionByName(requestDto.getRegion());
        Plan plan = PlanConverter.toPlan(requestDto, region);
        planRepository.save(plan);
        createNewPlanUser(plan, user);

        return plan;
    }

    @Transactional
    public List<PlanLocation> createPlanLocations(CreatePlanLocationListDto requestDto, Plan plan) {
        List<PlanLocation> planLocationList = requestDto.getPlanLocationDtos()
                .stream()
                .map(planLocationDto
                        -> PlanConverter
                        .toPlanLocation(planLocationDto, locationService.findById(planLocationDto.getLocationId()),
                                plan))
                .toList();

        return planLocationRepository.saveAll(planLocationList);
    }

    @Transactional
    public List<PlanLocation> editPlanLocations(CreatePlanLocationListDto requestDto, Plan plan) {
        planLocationRepository.deleteAllByPlan(plan);

        return createPlanLocations(requestDto, plan);
    }

    @Transactional
    public Plan editPlan(PlanReqDto reqDto, Plan plan) {
        plan.modifyPlan(reqDto.getTitle(), reqDto.getStartDate(), reqDto.getEndDate(),
                reqDto.getAdultCount(), reqDto.getChildCount(), reqDto.getVehicle());

        return planRepository.save(plan);
    }

    // 해쉬태그를 포함한 여행계획 상세정보 반환
    public Plan findPlanById(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.PLAN_NOT_FOUND));

        return plan;
    }

    // 여행 day 일차의 계획 반환
    public List<PlanLocation> findPlanLocationByDay(Plan plan, Long day) {
        return planLocationRepository.findAllByPlanAndTravelDay(plan, day);
    }

    // 여행 모든 일차의 계획 반환
    public List<PlanLocation> findPlanEveryDay(Plan plan) {
        return planLocationRepository.findAllByPlan(plan);
    }

    // 조회수 증가 시키고 plan 반환
    @Transactional
    public Plan increaseViewCount(Plan plan) {
        viewCountRepository.save(ViewCount.builder().plan(plan).build());

        plan.updateHits(viewCountRepository.countAllByPlan(plan));
        return planRepository.save(plan);
    }

    // 새로운 PlanUser 만들고 반환하는 메서드 추가
    @Transactional
    public PlanUser createNewPlanUser(Plan plan, User user) {
        return planUserRepository.save(PlanUser.builder().plan(plan).user(user).build());
    }

    public Plan findPlanByInviteCode(String inviteCode) {
        return planRepository.findByInviteCode(UUID.fromString(inviteCode))
                .orElseThrow(() -> GeneralException.of(ErrorCode.WRONG_INVITE_CODE));
    }

    public Page<Plan> getPlanList(Integer page, Long regionId, Integer way) {

        Region region = regionService.findRegionById(regionId);

        if (way == 1) {  // 좋아요 순
            return planRepository
                    .findAllByRegion(region, PageRequest.of(page, 12, Sort.by("likeCount").descending()));
        } else if (way == 2) {  // 조회 많은 순
            return planRepository
                    .findAllByRegion(region, PageRequest.of(page, 12, Sort.by("hits").descending()));
        } else if (way == 3) {  // 최신 순
            return planRepository
                    .findAllByRegion(region, PageRequest.of(page, 12, Sort.by("createdAt").descending()));
        } else {
            throw GeneralException.of(ErrorCode.WRONG_SORTING_WAY);
        }
    }

}
