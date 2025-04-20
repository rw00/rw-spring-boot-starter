package com.rw.apps.starter.common.security.jwt;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.jwt")
public record JwtConfigProperties(String secret, Duration expiration) {
}
