package com.rw.apps.starter.accounts.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Roles {
    USER, ADMIN;

    private static final Map<Roles, GrantedAuthority>
            AUTHORITY_MAP = Arrays.stream(Roles.values())
                                  .collect(Collectors.toMap(Function.identity(),
                                                            Roles::createGrantedAuthority));

    public GrantedAuthority toGrantedAuthority() {
        return AUTHORITY_MAP.get(this);
    }

    private static GrantedAuthority createGrantedAuthority(Roles role) {
        return new SimpleGrantedAuthority("ROLE_" + role.name());
    }
}
