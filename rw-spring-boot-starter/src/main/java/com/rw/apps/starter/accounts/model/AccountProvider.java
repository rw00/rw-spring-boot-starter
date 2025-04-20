package com.rw.apps.starter.accounts.model;

import org.apache.commons.lang3.EnumUtils;

public enum AccountProvider {
    LOCAL, GOOGLE, FACEBOOK;

    public static AccountProvider fromRegistrationId(String registrationId) {
        String providerName = null;
        if (registrationId != null) {
            providerName = registrationId.toUpperCase();
        }
        return EnumUtils.getEnum(AccountProvider.class, providerName, LOCAL);
    }
}
