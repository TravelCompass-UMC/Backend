package com.travelcompass.api.plan.dto;

import lombok.*;


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
}
