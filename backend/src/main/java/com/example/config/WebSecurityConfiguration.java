package com.example.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

    @Value("${react.origin}")
    private String reactOriginHost;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        http.formLogin(
                c -> c.loginPage("/api/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
        );
        http.logout(
                c -> c.logoutUrl("/api/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
        );
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
        );
        http.authorizeHttpRequests(
                c -> c
                        .requestMatchers(HttpMethod.GET ,"/api/customer").hasRole("CUSTOMER")
                        .requestMatchers("/api/customer/orders").hasRole("CUSTOMER")
                        .requestMatchers("/api/logout").authenticated()
                        .anyRequest().permitAll()
        );
        http.cors(c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of(reactOriginHost));
                config.setAllowCredentials(true);
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            };
            c.configurationSource(source);
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
