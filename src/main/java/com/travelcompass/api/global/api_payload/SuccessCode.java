package com.travelcompass.api.global.api_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode {
    // Common
    OK(HttpStatus.OK, "COMMON_200", "Success"),
    CREATED(HttpStatus.CREATED, "COMMON_201", "Created"),

    // User
    USER_CREATED(HttpStatus.CREATED, "USER_201", "회원가입이 완료되었습니다."),
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "USER_200", "로그아웃 되었습니다."),
    //USER_PASSWORD_CHANGE_SUCCESS(HttpStatus.OK, "USER_200", "비밀번호가 변경되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "USER_200", "회원탈퇴가 완료되었습니다."),

    //Plan
    PLAN_CREATED(HttpStatus.CREATED, "PLAN_2011", "여행계획이 생성되었습니다."),
    PLAN_INVITE_SUCCESS(HttpStatus.CREATED, "PLAN_2012", "초대가 완료되었습니다."),
    PLAN_VIEW_SUCCESS(HttpStatus.OK, "PLAN_2001", "조회가 완료되었습니다."),
    PLAN_MODIFIED(HttpStatus.OK,"PLAN_2002", "수정이 완료되었습니다."),
    PLAN_LIKE_SUCCESS(HttpStatus.OK, "PLAN_2021", "여행계획 좋아요가 완료되었습니다."),
    PLAN_UNLIKE_SUCCESS(HttpStatus.OK, "PLAN_2022", "여행계획 좋아요 취소가 완료되었습니다."),
    PLAN_LIKE_COUNT_SUCCESS(HttpStatus.OK, "PLAN_2023", "좋아요 수가 조회되었습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(true)
                .code(this.code)
                .message(this.message)
                .build();
    }
}
