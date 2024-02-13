package com.travelcompass.api.plan.converter;

import com.travelcompass.api.hashtag.domain.Hashtag;
import com.travelcompass.api.hashtag.domain.HashtagPlan;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.domain.PlanVehicle;
import com.travelcompass.api.plan.dto.PlanRequestDto.PlanReqDto;
import com.travelcompass.api.plan.dto.PlanRequestDto.CreatePlanLocationDto;
import com.travelcompass.api.plan.dto.PlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.DetailPlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanListResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanLocationListDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.SimplePlanLocationDto;
import com.travelcompass.api.region.domain.Region;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.travelcompass.api.plan.dto.PlanResponseDto.*;

@NoArgsConstructor
public class PlanConverter {

    public static Plan toPlan(PlanReqDto request, Region region){

        Long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());

        return Plan.builder()
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .vehicle(PlanVehicle.valueOf(request.getVehicle()))
                .region(region)
                .inviteCode(UUID.randomUUID())
                .adultCount(request.getAdultCount())
                .childCount(request.getChildCount())
                .hits(0L)
                .days(days)
                .likeCount(0L)
                .hashtagPlans(new ArrayList<>())
                .build();
    }

    public static PlanLocation toPlanLocation(CreatePlanLocationDto request, Location location, Plan plan){
        return PlanLocation.builder()
                .arrival(request.getArrival())
                .spendTime(request.getSpendTime())
                .travelDay(request.getTravelDay())
                .location(location)
                .plan(plan)
                .build();
    }

    public static DetailPlanResponseDto detailPlanResponseDto(Plan plan, List<String> hashtag){
        return DetailPlanResponseDto.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .inviteCode(String.valueOf(plan.getInviteCode()))
                .vehicle(String.valueOf(plan.getVehicle()))
                .adultCount(plan.getAdultCount())
                .childCount(plan.getChildCount())
                .hits(plan.getHits())
                .likeCount(plan.getLikeCount())
                .region(plan.getRegion().getName())
                .hashtag(hashtag)
                .build();
    }

    public static SimplePlanLocationDto simplePlanLocationDto(PlanLocation planLocation){
        return SimplePlanLocationDto.builder()
                .id(planLocation.getId())
                .arrival(planLocation.getArrival())
                .spendTime(planLocation.getSpendTime())
                .travelDay(planLocation.getTravelDay())
                .locationId(planLocation.getLocation().getId())
                .build();
    }

    public static PlanLocationListDto planLocationListDto(List<PlanLocation> planLocationList, DetailPlanResponseDto plan){
        List<SimplePlanLocationDto> locationDtos = planLocationList.stream()
                .map(PlanConverter::simplePlanLocationDto).collect(Collectors.toList());

        return PlanLocationListDto.builder()
                .plan(plan)
                .planLocations(locationDtos)
                .build();
    }

    public static SimplePlanDto simplePlanDto(Plan plan){
        List<String> hashtags = plan.getHashtagPlans().stream().map(HashtagPlan::getHashtag).toList()
                .stream().map(Hashtag::getName).toList();

        return SimplePlanDto.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .vehicle(String.valueOf(plan.getVehicle()))
                .hits(plan.getHits())
                .likeCount(plan.getLikeCount())
                .region(plan.getRegion().getName())
                .hashtag(hashtags)
                .build();
    }

    public static PlanListResponseDto planListResponseDto(Page<Plan> plans){

        List<SimplePlanDto> planDtos = plans.stream().map(PlanConverter::simplePlanDto).toList();

        return PlanListResponseDto.builder()
                .isLast(plans.isLast())
                .isFirst(plans.isFirst())
                .totalPage(plans.getTotalPages())
                .totalElements(plans.getTotalElements())
                .listSize(plans.getSize())
                .planList(planDtos)
                .build();


    }
}
