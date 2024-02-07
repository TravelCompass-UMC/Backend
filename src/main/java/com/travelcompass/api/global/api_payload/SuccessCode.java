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
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "USER_202", "로그아웃 되었습니다."),
    USER_REISSUE_SUCCESS(HttpStatus.OK, "USER_203", "토큰 재발급이 완료되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "USER_204", "회원탈퇴가 완료되었습니다."),
    //USER_PASSWORD_CHANGE_SUCCESS(HttpStatus.OK, "USER_205", "비밀번호가 변경되었습니다."),

    // Plan
    PLAN_CREATED(HttpStatus.CREATED, "PLAN_2011", "여행계획이 생성되었습니다."),
    PLAN_INVITE_SUCCESS(HttpStatus.CREATED, "PLAN_2012", "초대가 완료되었습니다."),
    PLAN_VIEW_SUCCESS(HttpStatus.OK, "PLAN_2001", "조회가 완료되었습니다."),
    PLAN_MODIFIED(HttpStatus.OK,"PLAN_2002", "수정이 완료되었습니다."),
    PLAN_LIKE_SUCCESS(HttpStatus.OK, "PLAN_2021", "여행계획 좋아요가 완료되었습니다."),
    PLAN_UNLIKE_SUCCESS(HttpStatus.OK, "PLAN_2022", "여행계획 좋아요 취소가 완료되었습니다."),
    PLAN_LIKE_COUNT_SUCCESS(HttpStatus.OK, "PLAN_2023", "좋아요 수가 조회되었습니다."),

    // Location
    LOCATION_LIKE_SUCCESS(HttpStatus.OK, "LOCATION_2021", "장소 좋아요가 완료되었습니다."),
    LOCATION_UNLIKE_SUCCESS(HttpStatus.OK, "LOCATION_2022", "장소 좋아요 취소가 완료되었습니다."),
    LOCATION_LIKE_COUNT_SUCCESS(HttpStatus.OK, "LOCATION_2023", "좋아요 수가 조회되었습니다."),

    //Mypage
    MYPAGE_INFO_VIEW_SUCCESS(HttpStatus.OK, "MYPAGE_2001", "나의 정보 조회가 완료되었습니다."),
    MYPAGE_PLAN_LIST_VIEW_SUCCESS(HttpStatus.OK, "MYPAGE_2002", "나의 여행계획 조회가 완료되었습니다."),
    MYPAGE_LIKED_PLAN_VIEW_SUCCESS(HttpStatus.OK, "MYPAGE_2003", "내가 좋아요한 여행계획 조회가 완료되었습니다."),
    MYPAGE_LIKED_LOCATION_VIEW_SUCCESS(HttpStatus.OK, "MYPAGE_2004", "내가 좋아요한 장소 조회가 완료되었습니다."),

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
