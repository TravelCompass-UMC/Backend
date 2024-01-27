package com.travelcompass.api.global.config;

import com.travelcompass.api.oauth.jwt.JwtTokenFilter;
import com.travelcompass.api.oauth.OAuth2SuccessHandler;
import com.travelcompass.api.oauth.OAuth2UserServiceImpl;
import com.travelcompass.api.oauth.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    protected SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers("/users/**", "/regions/**", "/locations/**", "/plans/**", "/me/**") //"/oauth/naver/**"
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/users/login") //비인증 사용자를 이동시킬 로그인 페이지
                        .successHandler(oAuth2SuccessHandler) //인증 성공 후 jwt 생성, 사용자 정보 db에 등록
                        //.defaultSuccessUrl("/users/main") //로그인(일정 부분) 성공하면 특정 화면으로 이동
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService) //사용자 데이터 처리
                        )
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtTokenFilter, AuthorizationFilter.class); //UsernamePasswordAuthenticationFilter

        return http.build();
    }
}