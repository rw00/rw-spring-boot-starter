package com.rw.apps.starter.common.security.util;

import com.rw.apps.starter.accounts.model.Authorities;
import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public final class AdminUtils {
    public static final String ADMIN_API_TAG = "admin-api";

    private AdminUtils() {
    }

    public static boolean isAdmin(BaseAccountEntity account) {
        return isAdminAuthority(account.getAuthorities());
    }

    public static boolean isAdminAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(Authorities.ROLE_ADMIN);
    }
}
