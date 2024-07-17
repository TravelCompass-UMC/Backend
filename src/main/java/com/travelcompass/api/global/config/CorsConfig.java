package com.travelcompass.api.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:8080", "https://travel-compass.netlify.app", "http://travel-compass.netlify.app", "https://travel-compass.persi0815.site", "http://travel-compass.persi0815.site")
                    .allowedMethods("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
}
