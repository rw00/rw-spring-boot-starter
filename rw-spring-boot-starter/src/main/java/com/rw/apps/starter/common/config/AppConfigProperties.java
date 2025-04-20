package com.rw.apps.starter.common.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigProperties {
    @ConfigurationProperties("app.admins")
    public record AdminsAccountsConfigProperties(List<BaseAccountConfigProps> accounts) {
    }

    @ConfigurationProperties("app.server")
    public record ServerConfigProperties(String baseUrl) {
    }

    @ConfigurationProperties("app.frontend")
    public record FrontendConfigProperties(String baseUrl) {
    }
}
