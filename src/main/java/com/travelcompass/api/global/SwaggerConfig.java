package com.travelcompass.api.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Travel Compass API")
                .description("Travel Compass API 명세서")
                .version("v1.0.0");

        return new OpenAPI()
                .components(new io.swagger.v3.oas.models.Components())
                .info(info);
    }
}
