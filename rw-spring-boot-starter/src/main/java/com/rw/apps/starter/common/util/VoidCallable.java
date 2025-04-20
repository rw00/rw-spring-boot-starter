package com.rw.apps.starter.common.util;

import java.util.concurrent.Callable;

public interface VoidCallable extends Callable<Void> {
    @Override
    default Void call() throws Exception {
        callVoid();
        return null;
    }

    void callVoid() throws Exception;
}
