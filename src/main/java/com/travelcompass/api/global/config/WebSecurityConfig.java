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
import lombok.RequiredArgsConstructor;

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
                        .requestMatchers("/health", "/oauth2/authorization/naver", "/users/**", "/regions/**", "/locations/**", "/plans/**", "/me/**")
                        .permitAll()
                        //.anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        //.loginPage("/users/login")
                        //.loginPage("http://dev.enble.site/oauth2/authorization/naver") //비인증 사용자를 이동시킬 로그인 페이지
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

/*
package com.travelcompass.api.global.config;
import com.travelcompass.api.oauth.jwt.JwtTokenFilter;
import com.travelcompass.api.oauth.OAuth2SuccessHandler;
import com.travelcompass.api.oauth.OAuth2UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;

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
                        .requestMatchers("/oauth2/authorization/naver", "/users/**", "/regions/**", "/locations/**", "/plans/**", "/me/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        //.loginPage("/users/login")
                        .loginPage("http://dev.enble.site/oauth2/authorization/naver") //비인증 사용자를 이동시킬 로그인 페이지
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
                .addFilterBefore(jwtTokenFilter, AuthorizationFilter.class) //UsernamePasswordAuthenticationFilter

                // OAuth2 Authorization Code Grant 설정 추가
                .oauth2Client(oauth2Client -> oauth2Client
                        .authorizationCodeGrant(authorizationCodeGrant -> authorizationCodeGrant
                                .authorizationRequestRepository(authorizationRequestRepository())
                                .authorizationRequestResolver(authorizationRequestResolver())
                                .accessTokenResponseClient(tokenResponseClient())
                        )
                );
        return http.build();
    }

    // OAuth2 Authorization Code Grant에 필요한 빈을 제공
    // 필요에 따라 커스터마이징이 필요할 수 있음

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
    @Bean
    public OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
        return new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository(),
                "/oauth2/authorization"
        );
    }
    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> tokenResponseClient() {
        return new DefaultAuthorizationCodeTokenResponseClient();
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(naverClientRegistration());
    }

    private ClientRegistration naverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId("3tVKSO15tNGbkeZJf8eE")
                .clientSecret("zHvANLwWHH")
                .redirectUri("http://dev.enble.site/login/oauth2/code/naver")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 수정된 부분
                .clientName("Naver")
                .build();
    }
}
 */