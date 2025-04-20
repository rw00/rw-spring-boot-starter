package com.rw.apps.starter.common.security.config;

import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import com.rw.apps.starter.accounts.service.BaseAccountsService;
import com.rw.apps.starter.accounts.validation.AccountStatusValidationService;
import com.rw.apps.starter.common.security.model.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final BaseAccountsService<?> accountsService;
    private final AccountStatusValidationService accountStatusValidationService;

    public CustomUserDetailsService(BaseAccountsService<?> accountsService,
                                    AccountStatusValidationService accountStatusValidationService) {
        this.accountsService = accountsService;
        this.accountStatusValidationService = accountStatusValidationService;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        log.atInfo().addKeyValue("username", username).log("Loading account");

        BaseAccountEntity accountEntity = accountsService.getAccountByEmail(username);
        accountStatusValidationService.checkAccountStatus(accountEntity);

        return new CurrentUser(accountEntity.getId(),
                               accountEntity.getEmail(),
                               accountEntity.getPassword(),
                               accountEntity.getAuthorities(),
                               accountEntity.isEnabled());
    }
}
