package com.rw.apps.starter.accounts.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authorities implements GrantedAuthority {
    ROLE_USER(Roles.USER.toGrantedAuthority()),
    ROLE_ADMIN(Roles.ADMIN.toGrantedAuthority());
    private final GrantedAuthority grantedAuthority;

    Authorities(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    @Override
    public String getAuthority() {
        return grantedAuthority.getAuthority();
    }
}
