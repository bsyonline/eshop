/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
@EnableCaching
public class EshopCacheApp {
    public static void main(String[] args) {
        SpringApplication.run(EshopCacheApp.class, args);
    }
}
