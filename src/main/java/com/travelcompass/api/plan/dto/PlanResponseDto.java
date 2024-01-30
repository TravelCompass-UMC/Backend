package com.travelcompass.api.plan.dto;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import com.travelcompass.api.plan.domain.PlanVehicle;
import com.travelcompass.api.region.domain.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
public class PlanResponseDto {

    @Schema(description = "DetailPlanResponseDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailPlanResponseDto{
        @Schema(description = "여행계획 id")
        private Long id;

        @Schema(description = "여행계획 제목")
        private String title;

        @Schema(description = "여행 시작일", example = "2024-01-01", pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @Schema(description = "여행 종료일", example = "2024-01-03", pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        @Schema(description = "초대를 위한 유니크한 코드")
        private String inviteCode;

        @Schema(description = "이동수단", example = "PUBLIC", allowableValues = {"PUBLIC", "PRIVATE"})
        private String vehicle;

        @Schema(description = "여행 지역명", example = "서울")
        private String region;

        @Schema(description = "여행하는 어른의 인원수")
        private Long adultCount;

        @Schema(description = "여행하는 어린이의 인원수")
        private Long childCount;

        @Schema(description = "조회수")
        private Long hits;

        @Schema(description = "좋아요 수")
        private Long likeCount;

        @Schema(description = "해쉬태그 리스트")
        private List<String> hashtag;
    }

    @Schema(description = "SimplePlanDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimplePlanDto{
        @Schema(description = "여행계획 id")
        private Long id;

        @Schema(description = "여행계획 제목")
        private String title;


        @Schema(description = "여행 시작일", example = "2024-01-01", pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @Schema(description = "여행 종료일", example = "2024-01-03", pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        @Schema(description = "이동수단", example = "PUBLIC", allowableValues = {"PUBLIC", "PRIVATE"})
        private String vehicle;

        @Schema(description = "여행 지역명", example = "서울")
        private String region;

        @Schema(description = "조회수")
        private Long hits;

        @Schema(description = "좋아요 수")
        private Long likeCount;

        @Schema(description = "해쉬태그 리스트")
        private List<String> hashtag;
    }

    @Schema(description = "PlanListResponseDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanListResponseDto{
        @Schema(description = "마지막 페이지가 맞으면 참, 아니면 거짓")
        private Boolean isLast;

        @Schema(description = "첫번째 페이지가 맞으면 참, 아니면 거짓")
        private Boolean isFirst;

        @Schema(description = "총 페이지 수")
        private Integer totalPage;

        @Schema(description = "총 Plan 수")
        private Long totalElements;

        @Schema(description = "planList의 크기")
        private Integer listSize;

        @Schema(description = "여행계획 리스트, 한 번 요청에 12개씩 전달함")
        private List<SimplePlanDto> planList;
    }

    @Schema(description = "SimplePlanLocationDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimplePlanLocationDto{
        @Schema(description = "여행 계획 장소의 id")
        private Long id;

        @Schema(description = "도착 시간", example = "13:00", pattern = "HH-MM") //
        private String arrival;

        @Schema(description = "소요 시간", example = "02:00", pattern = "HH-MM") //
        private Long spendTime;

        @Schema(description = "여행 일차 (첫날이 0)", example = "1")
        private Long travelDay;

        @Schema(description = "이 장소의 id")
        private Long locationId;
    }

    @Schema(description = "PlanLocationListDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanLocationListDto{
        @Schema(description = "DetailPlanResponseDto")
        DetailPlanResponseDto plan;

        @Schema(description = "List<SimplePlanLocationDto>")
        List<SimplePlanLocationDto> planLocations;
    }
}
