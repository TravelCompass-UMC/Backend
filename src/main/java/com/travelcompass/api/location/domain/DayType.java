package com.travelcompass.api.location.domain;

import lombok.Getter;

@Getter
public enum DayType {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String kor;

    DayType(String kor) {
        this.kor = kor;
    }

    public static DayType getWeek(String kor) {
        for (DayType dayType : DayType.values()) {
            if (dayType.getKor().equals(kor)) {
                return dayType;
            }
        }
        return null;
    }
}
