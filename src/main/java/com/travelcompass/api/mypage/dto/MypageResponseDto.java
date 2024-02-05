package com.travelcompass.api.mypage.dto;

import com.travelcompass.api.location.domain.LocationLike;
import com.travelcompass.api.plan.domain.PlanLike;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
}
