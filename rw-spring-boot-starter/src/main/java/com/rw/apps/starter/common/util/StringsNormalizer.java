package com.rw.apps.starter.common.util;

public final class StringsNormalizer {
    private StringsNormalizer() {
    }

    public static String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.trim();
    }

    public static String uppercase(String value) {
        if (value == null) {
            return null;
        }
        return value.toUpperCase();
    }

    public static String lowercase(String value) {
        if (value == null) {
            return null;
        }
        return value.toLowerCase();
    }
}
