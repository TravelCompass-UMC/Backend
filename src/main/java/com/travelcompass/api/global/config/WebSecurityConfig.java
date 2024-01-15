package com.travelcompass.api.global.config;

import com.travelcompass.api.oauth.jwt.JwtTokenFilter;
import com.travelcompass.api.oauth.OAuth2SuccessHandler;
import com.travelcompass.api.oauth.OAuth2UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2UserServiceImpl oAuth2UserService;

    public WebSecurityConfig(
            JwtTokenFilter jwtTokenFilter,
            OAuth2SuccessHandler oAuth2SuccessHandler,
            OAuth2UserServiceImpl oAuth2UserService
    ) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.oAuth2UserService = oAuth2UserService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers("/token/**", "/views/**").permitAll()
                )
                        /*.requestMatchers("/token/**", "/views/**", "/").permitAll() //누구나
                        //.requestMatchers("/users/login").anonymous() //로그인 안된 사람만
                        .anyRequest().authenticated() //로그인한 사람만*/

                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/views/login")
                        //handler: 인증 성공시에 사용할 handler 객체 설정
                        //endPoint: 사용자 정보를 endPoint설정을 진행하는데, 지금은 사용자 정보 조회 후 동작을 정의하기 위해 userService를 등록하는 용도로 사용
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, AuthorizationFilter.class);

        return http.build();
    }
}
