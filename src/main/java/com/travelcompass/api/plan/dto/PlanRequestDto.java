package com.travelcompass.api.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
public class PlanRequestDto {

    @Schema(description = "PlanReqDto")
    @Getter
    @NoArgsConstructor
    public static class PlanReqDto {
        @Schema(description = "여행계획 제목", example = "예시 제목입니다.")
        @NotBlank
        private String title;

        @Schema(description = "이동수단", example = "PUBLIC", allowableValues = {"PUBLIC", "PRIVATE"})
        @NotNull
        private String vehicle;

        @Schema(description = "여행 시작일", example = "2024-01-01", pattern = "yyyy-MM-dd") //
        @NotNull
        private String startDate;

        @Schema(description = "여행 종료일", example = "2024-01-03", pattern = "yyyy-MM-dd") //
        @NotNull
        private String endDate;

        @Schema(description = "여행 지역명", example = "서울")
        private String region;

        @Schema(description = "여행하는 어른의 인원수")
        private Long adultCount;

        @Schema(description = "여행하는 어린이의 인원수")
        private Long childCount;

        @Schema(description = "추가할 해쉬태그")
        private List<String> hashtags;
    }

    @Schema(description = "CreatePlanLocationDto")
    @Getter
    @NoArgsConstructor
    public static class CreatePlanLocationDto {
        @Schema(description = "추가하는 장소의 id")
        private Long locationId;

        @Schema(description = "도착 시간", example = "13:00", pattern = "HH-MM") //
        private String arrival;

        @Schema(description = "소요 시간", example = "02:00", pattern = "HH-MM") //
        private Long spendTime;

        @Schema(description = "여행 일차 (첫날이 0)", example = "1")
        private Long travelDay;
    }

    @Schema(description = "CreatePlanLocationListDto")
    @Getter
    @NoArgsConstructor
    public static class CreatePlanLocationListDto {
        @Schema(description = "CreatePlanLocationDto의 리스트")
        List<CreatePlanLocationDto> planLocationDtos;
    }

    @Schema(description = "CreatePlanDto")
    @Getter
    @NoArgsConstructor
    public static class CreatePlanDto {

        @Schema(description = "PlanReqDto")
        PlanReqDto planReqDto;

        @Schema(description = "CreatePlanLocationListDto")
        CreatePlanLocationListDto planLocationListDto;
    }
}
