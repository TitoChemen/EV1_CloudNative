package com.tuempresa.inventarioservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withJwkSetUri("https://login.microsoftonline.com/8680c901-cdee-41fb-b489-95b35c8ffaf0/discovery/v2.0/keys")
                .build();

        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators
                .createDefaultWithIssuer("https://sts.windows.net/8680c901-cdee-41fb-b489-95b35c8ffaf0/");

        jwtDecoder.setJwtValidator(withIssuer);
        return jwtDecoder;
    }
}