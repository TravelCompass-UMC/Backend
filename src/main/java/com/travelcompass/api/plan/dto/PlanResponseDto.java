package com.travelcompass.api.plan.dto;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.domain.PlanVehicle;
import com.travelcompass.api.region.domain.Region;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
public class PlanResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePlanResultDto{
        Long planId;

        // 생성된 초대코드를 결과에 함께 넣어줘야 할듯
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailPlanResponseDto{
        private Long id;
        private String title;
        private String startDate;
        private String endDate;
        private String inviteCode; // 초대를 위한 유니크한 코드
        private String vehicle;
        private String region;

        private List<String> hashtag;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimplePlanLocationDto{
        private Long id;
        private String arrival;
        private Long travelDay;
        private Location location;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanLocationListDto{
        DetailPlanResponseDto plan;
        List<SimplePlanLocationDto> planLocations;
    }
}
