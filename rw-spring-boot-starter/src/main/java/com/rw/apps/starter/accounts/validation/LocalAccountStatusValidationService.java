package com.rw.apps.starter.accounts.validation;

import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import com.rw.apps.starter.common.env.cfg.AppConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.stereotype.Service;

@Service
@Profile(AppConfig.LOCAL_PROFILE)
public class LocalAccountStatusValidationService implements AccountStatusValidationService {
    @Override
    public void checkAccountStatus(BaseAccountEntity accountEntity) {
        if (!accountEntity.isEnabled()) {
            throw new AccountStatusException("Account is disabled") {
            };
        }
    }
}
