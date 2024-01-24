package com.travelcompass.api.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
public class PlanRequestDto {

    @Getter
    @NoArgsConstructor
    public static class CreatePlanDto {
        @NotBlank
        private String title;
        @NotNull
        private String vehicle;
        @NotNull
        private String startDate;
        @NotNull
        private String endDate;
    }
}
