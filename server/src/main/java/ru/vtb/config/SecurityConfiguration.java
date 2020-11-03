package ru.vtb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static ru.vtb.config.rest.ApiConstant.REFRESH_URL;
import static ru.vtb.config.rest.ApiConstant.STATUS_URL;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    public static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_PREFIX = "ROLE_";
    public static final String ADMIN_AUTHORITY = ROLE_PREFIX + ROLE_ADMIN;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .httpBasic()
                .and().authorizeExchange()
                .pathMatchers(STATUS_URL).authenticated()
                .pathMatchers(REFRESH_URL).hasAuthority(ADMIN_AUTHORITY)
                .pathMatchers("/api/**", "/**").permitAll()
                .and()
                .formLogin()
                .and()
                .build();
    }
}
