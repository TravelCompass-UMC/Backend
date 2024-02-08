package com.travelcompass.api.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class MypageResponseDto {

    @Schema(description = "MyInfoDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyInfoDto{
        private Long id;
        private String password;
        private String email;
        private String nickname;
    }

    @Schema(description = "MypageLocationDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageLocationDto{
        @Schema(description = "장소 id")
        private Long id;

        @Schema(description = "장소명")
        private String name;

        @Schema(description = "장소 타입")
        private String locationType;

        @Schema(description = "장소 주소")
        private String address;

        @Schema(description = "장소 좋아요 수")
        private Long likeCount;

        @Schema(description = "장소 별점")
        private Double star;
    }


    @Schema(description = "MyLocationListResponseDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyLocationListResponseDto{
        @Schema(description = "마지막 페이지가 맞으면 참, 아니면 거짓")
        private Boolean isLast;

        @Schema(description = "첫번째 페이지가 맞으면 참, 아니면 거짓")
        private Boolean isFirst;

        @Schema(description = "총 페이지 수")
        private Integer totalPage;

        @Schema(description = "총 location 수")
        private Long totalElements;

        @Schema(description = "locationList의 크기")
        private Integer listSize;

        @Schema(description = "여행계획 리스트, 한 번 요청에 12개씩 전달함")
        private List<MypageLocationDto> locationList;
    }
}
