package com.example.saju.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvCheck {
    private final org.springframework.core.env.Environment env;

    @PostConstruct
    void check() {
        System.out.println("DB URL = " + env.getProperty("spring.datasource.url"));
        System.out.println("DB USER = " + env.getProperty("spring.datasource.username"));
    }
}
