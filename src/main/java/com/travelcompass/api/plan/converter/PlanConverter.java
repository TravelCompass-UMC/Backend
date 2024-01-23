package com.travelcompass.api.plan.converter;

import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.domain.PlanVehicle;
import com.travelcompass.api.plan.dto.PlanRequestDto;
import com.travelcompass.api.plan.dto.PlanRequestDto.CreatePlanDto;
import com.travelcompass.api.plan.dto.PlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.DetailPlanResponseDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.PlanLocationListDto;
import com.travelcompass.api.plan.dto.PlanResponseDto.SimplePlanLocationDto;
import com.travelcompass.api.region.domain.Region;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class PlanConverter {

    public static Plan toPlan(CreatePlanDto request, Region region, String inviteCode){
        return Plan.builder()
                .title(request.getTitle())
                .startDate(LocalDate.parse(request.getStartDate()))
                .endDate(LocalDate.parse(request.getEndDate()))
                .vehicle(PlanVehicle.valueOf(request.getVehicle()))
                .region(region)
                .inviteCode(inviteCode)
                .build();
    }

    public static DetailPlanResponseDto detailPlanResponseDto(Plan plan, List<String> hashtag){
        return DetailPlanResponseDto.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .startDate(String.valueOf(plan.getStartDate()))
                .endDate(String.valueOf(plan.getEndDate()))
                .inviteCode(plan.getInviteCode())
                .vehicle(String.valueOf(plan.getVehicle()))
                .region(plan.getRegion().getName())
                .hashtag(hashtag)
                .build();
    }

    public static SimplePlanLocationDto simplePlanLocationDto(PlanLocation planLocation){
        return SimplePlanLocationDto.builder()
                .id(planLocation.getId())
                .arrival(LocalTime.parse(planLocation.getArrival()))
                .travelDay(planLocation.getTravelDay())
                .location(planLocation.getLocation())
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
