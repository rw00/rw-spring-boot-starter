package com.rw.apps.starter.admin.api;

import com.rw.apps.starter.common.exceptions.NotFoundException;
import com.rw.apps.starter.common.security.util.AdminUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/caches")
@Tag(name = AdminUtils.ADMIN_API_TAG)
@ConditionalOnBean(CacheManager.class)
class AdminCacheApi {
    private final CacheManager cacheManager;

    AdminCacheApi(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @DeleteMapping("{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void clearCache(@PathVariable @NotBlank String name) {
        Cache cache = cacheManager.getCache(name);
        if (cache == null) {
            throw new NotFoundException("Cache not found");
        }
        cache.clear();
    }
}
