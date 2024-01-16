package com.travelcompass.api.location.domain;

import lombok.Getter;

@Getter
public enum Week {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String kor;

    Week(String kor) {
        this.kor = kor;
    }
}
