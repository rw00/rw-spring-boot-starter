package com.rw.apps.starter.test.config.security;

import com.rw.apps.starter.common.security.jwt.JwtAuthHeaderExtractor;
import com.rw.apps.starter.common.security.jwt.JwtConfigProperties;
import com.rw.apps.starter.common.security.jwt.JwtCurrentUserExtractor;
import com.rw.apps.starter.common.security.jwt.JwtService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtConfigProperties.class)
class SecurityConfig {

    @Bean
    JwtCurrentUserExtractor currentUserExtractor(JwtService jwtService) {
        return new JwtAuthHeaderExtractor(jwtService);
    }
}
