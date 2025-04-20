package com.rw.apps.starter.accounts.validation;

import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;

public interface AccountStatusValidationService {
    void checkAccountStatus(BaseAccountEntity accountEntity);
}
