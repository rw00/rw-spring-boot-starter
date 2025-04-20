package com.rw.apps.starter.common.security.jwt;

import com.rw.apps.starter.common.api.helper.AuthErrorResponseHelper;
import com.rw.apps.starter.common.config.AppConfigProperties.ServerConfigProperties;
import com.rw.apps.starter.common.env.EnvHelper;
import com.rw.apps.starter.common.security.config.CustomUserDetailsService;
import com.rw.apps.starter.common.security.filter.AdminAuthenticationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
class JwtAutoConfig {

    @Bean
    JwtService jwtService(JwtConfigProperties jwtConfigProperties) {
        return new JwtService(jwtConfigProperties);
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(JwtCurrentUserExtractor currentUserExtractor,
                                                    CustomUserDetailsService customUserDetailsService,
                                                    AuthErrorResponseHelper authErrorResponseHelper,
                                                    EnvHelper envHelper,
                                                    AdminAuthenticationFilter adminAuthFilter,
                                                    ServerConfigProperties serverConfigProperties) {
        return new JwtAuthenticationFilter(currentUserExtractor,
                                           customUserDetailsService,
                                           authErrorResponseHelper,
                                           envHelper,
                                           adminAuthFilter,
                                           serverConfigProperties);
    }

    @Bean
    @ConditionalOnBean(JwtAuthenticationFilter.class)
    SecurityFilterChain jwtSecurityFilterChain(HttpSecurity httpSecurity,
                                               AuthenticationManager authenticationManager,
                                               JwtAuthenticationFilter jwtFilter) throws Exception {
        httpSecurity
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable) // FIXME: enable CSRF protection
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
