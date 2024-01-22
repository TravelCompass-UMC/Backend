package com.travelcompass.api.oauth.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
