package com.rw.apps.starter.common.util;

public final class OperationUtil {
    private OperationUtil() {
    }

    public static void call(VoidCallable callable) {
        try {
            callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
