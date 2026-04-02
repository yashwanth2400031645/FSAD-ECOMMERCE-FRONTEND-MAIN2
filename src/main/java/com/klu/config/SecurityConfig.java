package com.klu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {}) // ✅ enable CORS
            .csrf(csrf -> csrf.disable()) // disable CSRF for API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/health").permitAll() // allow root and health check without authentication
                .anyRequest().permitAll() // allow all requests
            );

        return http.build();
    }

    // ✅ MAIN CORS CONFIGURATION (FIXED)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // ✅ IMPORTANT: allow FRONTEND URL (not backend)
        config.setAllowedOrigins(List.of(
            "https://ecommerce-frontend-main1-production.up.railway.app"
        ));

        // ✅ allow all required methods
        config.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // ✅ allow headers
        config.setAllowedHeaders(List.of("*"));

        // ✅ allow credentials (cookies, auth headers)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
