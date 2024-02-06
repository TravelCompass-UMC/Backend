package com.travelcompass.api.oauth.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.location.domain.LocationLike;
import com.travelcompass.api.plan.domain.PlanLike;
import com.travelcompass.api.plan.domain.PlanUser;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB 제약사항 추가
    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    private String email;
    private String nickname;

    // Naver, Kakao 등 소셜 로그인 제공자 문자값
    private String provider;
    // Naver, Kakao 등에서 사용자를 식별하기 위해 제공한 값
    private String providerId;

    @OneToMany(mappedBy = "user")
    private List<PlanLike> planLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LocationLike> locaionLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PlanUser> planUsers = new ArrayList<>();
}
