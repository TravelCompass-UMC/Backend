package com.travelcompass.api.plan.converter;

import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanGroup;
import com.travelcompass.api.plan.domain.PlanVehicle;
import com.travelcompass.api.plan.dto.PlanRequestDto;
import com.travelcompass.api.plan.dto.PlanRequestDto.CreatePlanDto;
import com.travelcompass.api.region.domain.Region;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class PlanConverter {

    public static Plan toPlan(CreatePlanDto request, Region region){
        return Plan.builder()
                .title(request.getTitle())
                .startDate(LocalDate.parse(request.getStartDate()))
                .endDate(LocalDate.parse(request.getEndDate()))
                .vehicle(PlanVehicle.valueOf(request.getVehicle()))
                .region(region)
                .build();
    }

}
