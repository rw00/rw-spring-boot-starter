package com.rw.apps.starter.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ConditionalOnClass(name = "com.github.benmanes.caffeine.cache.Caffeine")
class CachingConfig {
}
