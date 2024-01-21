package com.travelcompass.api.oauth.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB 제약사항 추가
    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    private String email;
    private String nickname;
    private String profile_image;

    // Naver, Kakao 등 소셜 로그인 제공자 문자값
    private String provider;
    // Naver, Kakao 등에서 사용자를 식별하기 위해 제공한 값
    private String providerId;

/*@Builder.Default
    private Boolean isDeleted = false;*/

}
