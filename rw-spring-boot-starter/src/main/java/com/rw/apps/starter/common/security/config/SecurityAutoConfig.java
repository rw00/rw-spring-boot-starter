package com.rw.apps.starter.common.security.config;

import com.rw.apps.starter.accounts.service.BaseAccountsService;
import com.rw.apps.starter.accounts.validation.AccountStatusValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class SecurityAutoConfig {

    @Bean
    @Primary
    public CustomUserDetailsService customUserDetailsService(BaseAccountsService<?> usersService,
                                                             AccountStatusValidationService accountStatusValidationService) {
        return new CustomUserDetailsService(usersService, accountStatusValidationService);
    }
}
