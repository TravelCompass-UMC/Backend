package com.travelcompass.api.oauth.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestDto {
    private String username;
    private String password;
}

