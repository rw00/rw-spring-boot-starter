package com.rw.apps.starter.location.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
class LocationServiceConfig {
    @Bean
    RestOperations locationServiceRestOperations(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.uriTemplateHandler(new DefaultUriBuilderFactory("https://api.country.is"))
                                  .connectTimeout(Duration.of(2, ChronoUnit.SECONDS))
                                  .readTimeout(Duration.of(2, ChronoUnit.SECONDS))
                                  .build();
    }
}
