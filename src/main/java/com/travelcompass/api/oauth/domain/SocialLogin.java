package com.travelcompass.api.oauth.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SocialLogin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //social_login_id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user_id; //external_id

    // 공통 소셜 로그인 필드
    private String accessToken;
    //private String refreshToken; - 사용 안할 수도

    // 어떤 소셜 미디어에서 로그인했는지 저장
    private String provider;

    // 각 소셜 미디어에서 받아올 수 있는 특별한 필드들
    private String naverUserId;
}