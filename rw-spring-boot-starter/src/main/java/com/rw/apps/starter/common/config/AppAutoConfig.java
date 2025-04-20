package com.rw.apps.starter.common.config;

import com.rw.apps.starter.common.config.AppConfigProperties.AdminsAccountsConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        AppConfigProperties.ServerConfigProperties.class,
        AppConfigProperties.FrontendConfigProperties.class,
        AdminsAccountsConfigProperties.class})
public class AppAutoConfig {
}
