package com.rw.apps.starter.common.api.token;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DefaultTokenGenerator implements TokenGenerator {
    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
