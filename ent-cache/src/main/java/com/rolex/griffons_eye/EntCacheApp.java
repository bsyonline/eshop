/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
public class EntCacheApp {
    public static void main(String[] args) {
        SpringApplication.run(EntCacheApp.class, args);
    }
}
