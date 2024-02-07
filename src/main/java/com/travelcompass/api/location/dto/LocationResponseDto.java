package com.travelcompass.api.location.dto;

import com.travelcompass.api.location.domain.LocationType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LocationResponseDto {

    private LocationResponseDto() {
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailLocationDto {

        @Schema(description = "장소 id")
        private Long id;

        @Schema(description = "장소명")
        private String name;

        @Schema(description = "장소 타입", example = "ATTRACTION", allowableValues = {"ATTRACTION",
                "RESTAURANT", "CAFE", "ACCOMMODATION"})
        private LocationType locationType;

        @Schema(description = "별점")
        private Double star;

        @Schema(description = "도로명 주소")
        private String roadNameAddress;

        @Schema(description = "영업시간 리스트")
        private List<BusinessHoursDto> businessHoursDtos;

        @Schema(description = "전화번호")
        private String tel;

        @Schema(description = "이미지 URL")
        private String imageUrl;

        @Schema(description = "위도")
        private Double latitude;

        @Schema(description = "경도")
        private Double longitude;
    }

}
