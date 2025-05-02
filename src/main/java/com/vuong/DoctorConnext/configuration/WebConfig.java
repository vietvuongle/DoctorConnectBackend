package com.vuong.DoctorConnext.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Cho tất cả API
                        .allowedOrigins(
                                "http://localhost:5175",
                                "http://localhost:5176"
                        ) // Cho phép frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức cho phép
                        .allowedHeaders("*") // Các header cho phép
                        .allowCredentials(true); // Cho phép gửi cookie nếu có
            }
        };
    }
}
