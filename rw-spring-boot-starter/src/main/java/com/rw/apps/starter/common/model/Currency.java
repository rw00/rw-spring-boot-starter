package com.rw.apps.starter.common.model;

public enum Currency {
    EUR("Euro"),
    USD("US Dollar"),
    SAR("Saudi Riyal"),
    AED("United Arab Emirates Dirham"),
    JOD("Jordanian Dinar"),
    AUD("Australian Dollar"),

    RUB("Russian Rouble"),
    ;

    private final String displayName;

    Currency(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
