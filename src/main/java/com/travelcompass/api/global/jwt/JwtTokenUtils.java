package com.travelcompass.api.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
// JWT 관련 기능들을 넣어두기 위한 기능성 클래스 - 생성, 유효성 검증
public class JwtTokenUtils {
    private final Key signingKey;
    private final JwtParser jwtParser;

    // server의 secret key 사용 -> 보완 강화, server의 부하 감소, 서버 확장성에 대한 이점
    public JwtTokenUtils(
            @Value("${jwt.secret}")
            String jwtSecret
    ) {
        this.signingKey
                = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        // JWT 번역기 만들기
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(this.signingKey)
                .build();
    }

    // JWT가 유효한지 판단
    // jjwt 라이브러리에서는 JWT를 해석하는 과정에서 유효하지 않으면 예외 발생
    public boolean validate(String token) {
        try {
            // 정당한 JWT면 true,
            jwtParser.parseClaimsJws(token); // parseClaimsJws: 암호화된 JWT를 해석하기 위한 메소드
            return true;
            // 정당하지 않은 JWT면 false
        } catch (Exception e) {
            log.warn("invalid jwt: {}", e.getClass());
            //만료 토큰 처리 필요
            return false;
        }
    }

    // JWT를 인자로 받고, 그 JWT를 해석해서 사용자 정보를 회수
    public Claims parseClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    // 주어진 사용자 정보를 바탕으로 JWT를 문자열로 생성
    public String generateToken(UserDetails userDetails) {
        // Claims: JWT에 담기는 정보의 단위를 Claim이라 부름
        //         Claims는 Claim들을 담기위한 Map의 상속 interface
        Claims jwtClaims = Jwts.claims()
                // 사용자 정보 등록
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now())) //생성된 시간
                .setExpiration(Date.from(Instant.now().plusSeconds(3600))); //만료 시간

        return Jwts.builder()
                .setClaims(jwtClaims) //Claims 설정
                .signWith(signingKey) //hmacSha로 sign
                .compact(); //토큰 생성
    }

}

