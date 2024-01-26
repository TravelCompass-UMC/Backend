package com.travelcompass.api.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
public class PlanRequestDto {

    @Getter
    @NoArgsConstructor
    public static class PlanReqDto {
        @NotBlank
        private String title;
        @NotNull
        private String vehicle;
        @NotNull
        private String startDate;
        @NotNull
        private String endDate;

        private String region;

        private Long adultCount;
        private Long childCount;

        private List<String> hashtags;
    }

    @Getter
    @NoArgsConstructor
    public static class CreatePlanLocationDto {
        private Long locationId;
        private String arrival;
        private Long spendTime;
        private Long travelDay;
    }

    @Getter
    @NoArgsConstructor
    public static class CreatePlanLocationListDto {
        List<CreatePlanLocationDto> planLocationDtos;
    }

    @Getter
    @NoArgsConstructor
    public static class CreatePlanDto {
        PlanReqDto planReqDto;
        CreatePlanLocationListDto planLocationListDto;
    }
}
