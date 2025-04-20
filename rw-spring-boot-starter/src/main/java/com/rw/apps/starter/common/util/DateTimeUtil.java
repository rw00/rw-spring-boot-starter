package com.rw.apps.starter.common.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
