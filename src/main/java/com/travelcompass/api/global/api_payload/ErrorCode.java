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

    // User
    WRONG_PASSWORD(HttpStatus.NOT_FOUND, "USER_404", "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "존재하지 않는 회원입니다."),
    UNMATCHED_PASSWORD(HttpStatus.NOT_FOUND, "USER_404", "비밀번호 확인이 일치하지 않습니다."),

    // Jwt
    TOKEN_NO_AUTH(HttpStatus.FORBIDDEN, "JWT_403", "권한 정보가 없는 토큰입니다."),
    IP_NOT_MATCHED(HttpStatus.FORBIDDEN, "JWT_404", "리프레시 토큰의 IP주소가 일치하지 않습니다."),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "JWT_404", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_401", "토큰 유효기간이 만료되었습니다."),
    WRONG_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "JWT_404", "일치하는 리프레시 토큰이 없습니다.");

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
