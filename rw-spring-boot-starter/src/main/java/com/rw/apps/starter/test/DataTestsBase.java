package com.rw.apps.starter.test;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class DataTestsBase {
    static final PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>(
            "postgres:16.6"
    ).withDatabaseName("app-db").withUsername("root").withPassword("not-secret");

    @BeforeAll
    static void setUp() {
        dbContainer.start();
    }

    @DynamicPropertySource
    static void dbConnectionProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", dbContainer::getJdbcUrl);
        registry.add("spring.datasource.username", dbContainer::getUsername);
        registry.add("spring.datasource.password", dbContainer::getPassword);
    }
}
