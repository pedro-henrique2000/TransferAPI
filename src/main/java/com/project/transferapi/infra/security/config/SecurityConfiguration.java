package com.project.transferapi.infra.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.project.transferapi.domain.entity.Permission.*;
import static com.project.transferapi.domain.entity.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final JWTAuthenticationFilter jwtFilterAuthentication;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("api/auth", "api/users")
                .permitAll()
                .requestMatchers("/api/management/**").hasAnyRole(ADMIN.name())
                .requestMatchers(GET, "/api/management/**").hasAnyAuthority(ADMIN_READ.name())
                .requestMatchers(POST, "/api/management/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/api/management/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/api/management/**").hasAnyAuthority(ADMIN_DELETE.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(this.authenticationProvider)
                .addFilterBefore(this.jwtFilterAuthentication, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
