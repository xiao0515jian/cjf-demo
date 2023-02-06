package com.cjf.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestServiceApplication {

    @Value("${spring.profiles.active}")
    private static String active;

    public static void main(String[] args) {
        SpringApplication.run(TestServiceApplication.class, args);
    }
}
