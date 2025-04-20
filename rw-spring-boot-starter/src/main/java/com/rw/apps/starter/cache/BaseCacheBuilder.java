package com.rw.apps.starter.cache;

import com.github.benmanes.caffeine.cache.Caffeine;

public final class BaseCacheBuilder {
    private BaseCacheBuilder() {
    }

    public static <K, V> Caffeine<K, V> defaultBuilder() {
        @SuppressWarnings("unchecked")
        Caffeine<K, V> caffeine = (Caffeine<K, V>) Caffeine.newBuilder()
                                                           .recordStats();
        return caffeine;
    }
}
