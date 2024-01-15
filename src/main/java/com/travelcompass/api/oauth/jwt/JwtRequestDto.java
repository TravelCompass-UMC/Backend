package com.travelcompass.api.oauth.jwt;

import lombok.Getter;

@Getter
public class JwtRequestDto {
    private String username;
    private String password;
}

