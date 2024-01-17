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

    public static Week getWeek(String kor) {
        for (Week week : Week.values()) {
            if (week.getKor().equals(kor)) {
                return week;
            }
        }
        return null;
    }
}
