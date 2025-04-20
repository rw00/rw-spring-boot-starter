package com.rw.apps.starter.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestBase extends DataTestsBase {

    @Autowired
    ObjectMapper objectMapper;

    protected String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Could not map to JSON", e);
        }
    }

    protected <T> T fromJsonString(Class<T> type, String str) {
        try {
            return objectMapper.readValue(str, type);
        } catch (Exception e) {
            throw new RuntimeException("Could not map from JSON", e);
        }
    }
}
