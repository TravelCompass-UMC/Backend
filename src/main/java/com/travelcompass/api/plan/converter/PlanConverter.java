package com.travelcompass.api.plan.converter;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.domain.PlanVehicle;
import com.travelcompass.api.plan.dto.PlanRequestDto.PlanReqDto;
import com.travelcompass.api.plan.dto.PlanRequestDto.CreatePlanLocationDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.DetailPlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanLocationListDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.SimplePlanLocationDto;
import com.travelcompass.api.region.domain.Region;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
public class PlanConverter {

    public static Plan toPlan(PlanReqDto request, Region region){
        return Plan.builder()
                .title(request.getTitle())
                .startDate(LocalDate.parse(request.getStartDate()))
                .endDate(LocalDate.parse(request.getEndDate()))
                .vehicle(PlanVehicle.valueOf(request.getVehicle()))
                .region(region)
                .inviteCode(UUID.randomUUID())
                .adultCount(request.getAdultCount())
                .childCount(request.getChildCount())
                .hits(0L)
                .build();
    }

    public static PlanLocation toPlanLocation(CreatePlanLocationDto request, Location location, Plan plan){
        return PlanLocation.builder()
                .arrival(LocalTime.parse(request.getArrival()))
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
                .startDate(String.valueOf(plan.getStartDate()))
                .endDate(String.valueOf(plan.getEndDate()))
                .inviteCode(String.valueOf(plan.getInviteCode()))
                .vehicle(String.valueOf(plan.getVehicle()))
                .adultCount(plan.getAdultCount())
                .childCount(plan.getChildCount())
                .hits(plan.getHits())
                .region(plan.getRegion().getName())
                .hashtag(hashtag)
                .build();
    }

    public static SimplePlanLocationDto simplePlanLocationDto(PlanLocation planLocation){
        return SimplePlanLocationDto.builder()
                .id(planLocation.getId())
                .arrival(String.valueOf(planLocation.getArrival()))
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
}
