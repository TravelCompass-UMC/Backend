package com.travelcompass.api.oauth.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
// 단순 아이디 비밀번호 외에 소셜 로그인을 통해 계정을 생성해보자.
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB 제약사항 추가
    @Column(nullable = false, unique = true)
    private String username;
    private String password; //encoding된 소셜 비밀번호

    private String email;
    //닉네임 추가?

    // 소셜 로그인 제공자 문자값
    private String provider;
    // Naver,에서 사용자를 식별하기 위해 제공한 값
    private String providerId;
}
