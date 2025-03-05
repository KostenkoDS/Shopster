package com.example.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

//@TestConfiguration
public class TestSecurityConfiguration {

//    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.formLogin(c -> c.disable());
        http.authorizeHttpRequests(c -> c.anyRequest().permitAll());
        http.csrf(c -> c.disable());
        return http.build();
    }
}
