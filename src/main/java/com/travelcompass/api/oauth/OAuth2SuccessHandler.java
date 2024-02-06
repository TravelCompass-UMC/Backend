package com.travelcompass.api.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelcompass.api.oauth.jwt.*;
//import com.travelcompass.api.oauth.repository.RefreshTokenRedisRepository;
import com.travelcompass.api.oauth.utils.IpUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
// OAuth2 통신이 성공적으로 끝났을 때 사용됨
// ID Provider에게 받은 정보를 바탕으로 JWT를 발급
// + 클라이언트가 저장할 수 있도록 특정 URL로 리다이렉트
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsManager userDetailsManager;
//    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public OAuth2SuccessHandler(
            JwtTokenUtils tokenUtils,
            UserDetailsManager userDetailsManager
//            RefreshTokenRedisRepository refreshTokenRedisRepository
    ) {
        this.tokenUtils = tokenUtils;
        this.userDetailsManager = userDetailsManager;
//        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
    }

    @Override
    // 인증 성공시 호출되는 메소드
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2UserServiceImpl에서 반환한 DefaultOAuth2User가 저장
        OAuth2User oAuth2User
                = (OAuth2User) authentication.getPrincipal();
        // 소셜 로그인을 한 새로운 사용자를 우리의 UserEntity로 전환
        String email = oAuth2User.getAttribute("email");
        String nickname = oAuth2User.getAttribute("nickname");
        String provider = oAuth2User.getAttribute("provider");
        String username
                = String.format("{%s}%s", provider, email.split("@")[0]);
        String providerId = oAuth2User.getAttribute("id").toString();

        // 처음으로 소셜 로그인한 사용자를 데이터베이스에 등록
        if(!userDetailsManager.userExists(username)) { //1. 최초 로그인인지 확인
            userDetailsManager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .password(providerId)
                    .email(email)
                    .nickname(nickname)
                    .provider(provider)
                    .providerId(providerId)
                    .build());
        }

        // JWT 생성 - access & refresh
        UserDetails details
                = userDetailsManager.loadUserByUsername(username);
        JwtDto jwt = tokenUtils.generateToken(details); //2. access, refresh token 생성 및 발급
        log.info("accessToken: {}", jwt.getAccessToken());
        log.info("refreshToken: {} ", jwt.getRefreshToken());

        // 유효기간 초단위 설정 후 redis에 refresh token save
//        Claims refreshTokenClaims = tokenUtils.parseClaims(jwt.getRefreshToken());
//        Long validPeriod
//                = refreshTokenClaims.getExpiration().toInstant().getEpochSecond()
//                - refreshTokenClaims.getIssuedAt().toInstant().getEpochSecond();
//        refreshTokenRedisRepository.save(
//                RefreshToken.builder()
//                        .id(username)
//                        .ip(IpUtil.getClientIp(request))
//                        .ttl(validPeriod)
//                        .refreshToken(jwt.getRefreshToken())
//                        .build()
//        );

        // 목적지 URL 설정 - 토큰 던짐
        String targetUrl = String.format(
                "http://dev.enble.site:8080/token?access-token=%s&refresh-token=%s", jwt.getAccessToken(), jwt.getRefreshToken()
        );
        // 실제 Redirect 응답 생성
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        /*
        response.sendRedirect(UriComponentsBuilder.fromUriString("http://localhost:8080/login/oauth2/code/naver")
        //react에서 로그인 성공 후 redirect 받을 페이지
        //login/oauth2/code/naver 페이지에 accessToken, refreshToken등의 필요한 정보를 쿼리스트링 방식으로 보냄
                .queryParam("accessToken", "accessToken")
                .queryParam("refreshToken", "refreshToken")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());


         */
    }
}


/*
package com.travelcompass.api.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelcompass.api.oauth.jwt.*;
import com.travelcompass.api.oauth.repository.RefreshTokenRedisRepository;
import com.travelcompass.api.oauth.utils.IpUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
// OAuth2 통신이 성공적으로 끝났을 때 사용됨
// ID Provider에게 받은 정보를 바탕으로 JWT를 발급
// + 클라이언트가 저장할 수 있도록 특정 URL로 리다이렉트
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsManager userDetailsManager;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public OAuth2SuccessHandler(
            JwtTokenUtils tokenUtils,
            UserDetailsManager userDetailsManager,
            RefreshTokenRedisRepository refreshTokenRedisRepository
    ) {
        this.tokenUtils = tokenUtils;
        this.userDetailsManager = userDetailsManager;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
    }

    //@Override
    // 인증 성공시 호출되는 메소드
    public OAuthResponseDto onAuthenticationSuccess(
            HttpServletRequest request,
            //HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2UserServiceImpl에서 반환한 DefaultOAuth2User가 저장
        OAuth2User oAuth2User
                = (OAuth2User) authentication.getPrincipal();
        // 소셜 로그인을 한 새로운 사용자를 우리의 UserEntity로 전환
        String email = oAuth2User.getAttribute("email");
        String nickname = oAuth2User.getAttribute("nickname");
        String provider = oAuth2User.getAttribute("provider");
        String username
                = String.format("{%s}%s", provider, email.split("@")[0]);
        String providerId = oAuth2User.getAttribute("id").toString();

        // 처음으로 소셜 로그인한 사용자를 데이터베이스에 등록
        if(!userDetailsManager.userExists(username)) { //1. 최초 로그인인지 확인
            userDetailsManager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .password(providerId)
                    .email(email)
                    .nickname(nickname)
                    .provider(provider)
                    .providerId(providerId)
                    .build());
        }

        // JWT 생성 - access & refresh
        UserDetails details
                = userDetailsManager.loadUserByUsername(username);
        JwtDto jwt = tokenUtils.generateToken(details); //2. access, refresh token 생성 및 발급
        log.info("accessToken: {}", jwt.getAccessToken());
        log.info("refreshToken: {} ", jwt.getRefreshToken());

        // 유효기간 초단위 설정 후 redis에 refresh token save
        Claims refreshTokenClaims = tokenUtils.parseClaims(jwt.getRefreshToken());
        Long validPeriod
                = refreshTokenClaims.getExpiration().toInstant().getEpochSecond()
                - refreshTokenClaims.getIssuedAt().toInstant().getEpochSecond();
        refreshTokenRedisRepository.save(
                RefreshToken.builder()
                        .id(username)
                        .ip(IpUtil.getClientIp(request))
                        .ttl(validPeriod)
                        .refreshToken(jwt.getRefreshToken())
                        .build()
        );

        return new OAuthResponseDto(jwt.getAccessToken(), jwt.getRefreshToken());
    }

    @Override
    // 인증 성공시 호출되는 메소드
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuthResponseDto oAuthResponseDto = onAuthenticationSuccess(request, authentication);

        // 컨트롤러로 OAuthResponseDto 반환

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(oAuthResponseDto));

        response.sendRedirect(UriComponentsBuilder.fromUriString("http://localhost:8080/login/oauth2/code/naver")
        //react에서 로그인 성공 후 redirect 받을 페이지
        //login/oauth2/code/naver 페이지에 accessToken, refreshToken등의 필요한 정보를 쿼리스트링 방식으로 보냄
                .queryParam("accessToken", "accessToken")
                .queryParam("refreshToken", "refreshToken")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());
    }
}
 */