package com.travelcompass.api.oauth.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class OAuthResponseDto {
    private String accessToken;
    private String refreshToken;

    public OAuthResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
