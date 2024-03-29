package com.travelcompass.api.global.api_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode {
    // Common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 서버 개발자에게 문의하세요."),

    // Address
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS_404", "주소를 찾을 수 없습니다."),

    // Region
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION_404", "지역을 찾을 수 없습니다."),

    // Location
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "LOCATION_404", "장소를 찾을 수 없습니다."),

    // LocationImage
    LOCATION_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "LOCATION_IMAGE_404", "장소 이미지를 찾을 수 없습니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다."),

    // Jwt
    WRONG_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "JWT_4041", "일치하는 리프레시 토큰이 없습니다."),
    IP_NOT_MATCHED(HttpStatus.FORBIDDEN, "JWT_4042", "리프레시 토큰의 IP주소가 일치하지 않습니다."),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "JWT_4043", "유효하지 않은 토큰입니다."),
    TOKEN_NO_AUTH(HttpStatus.FORBIDDEN, "JWT_4031", "권한 정보가 없는 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_4011", "토큰 유효기간이 만료되었습니다."),

    // Plan
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAN_404", "여행계획을 찾을 수 없습니다."),
    PLAN_LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND,"PLAN_4042","여행장소를 찾을 수 없습니다."),
    WRONG_INVITE_CODE(HttpStatus.NOT_FOUND, "PLAN_4043", "유효하지 않은 초대코드입니다."),
    WRONG_SORTING_WAY(HttpStatus.BAD_REQUEST, "PLAN_4001", "유효하지 않은 정렬 방법입니다."),
    WRONG_VEHICLE_PARAM(HttpStatus.BAD_REQUEST, "PLAN_4002", "유효하지 않은 교통수단 입니다."),
    FAIL_TO_PARSE_JSON(HttpStatus.INTERNAL_SERVER_ERROR, "PLAN_500", "json 파싱을 실패했습니다."),

    //Hashtag
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "HASHTAG_404", "해시태그를 찾을 수 없습니다."),

    //Mypage
    MYPAGE_WRONG_LOCATION_TYPE(HttpStatus.BAD_REQUEST, "MYPAGE_4001", "유효하지 않은 locationType 입니다."),
    MYPAGE_WRONG_SORTING_WAY(HttpStatus.BAD_REQUEST, "MYPAGE_4002", "유효하지 않은 정렬 방법입니다.")

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(false)
                .code(this.code)
                .message(this.message)
                .build();
    }
}
