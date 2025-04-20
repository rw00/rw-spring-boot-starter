package com.rw.apps.starter.accounts.model.reqres;

import com.rw.apps.starter.accounts.model.AccountProvider;
import com.rw.apps.starter.accounts.model.Authorities;
import java.util.Set;

public record AccountEntityView(
        String id,
        String email,
        boolean enabled,
        Set<Authorities> authorities,
        Set<AccountProvider> accountProviders) {
}
