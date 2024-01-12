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
