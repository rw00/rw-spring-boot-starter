package com.rw.apps.starter.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rw.apps.starter", "com.rw.apps.starter.test"})
public class RwStarterTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RwStarterTestApplication.class, args);
    }
}
