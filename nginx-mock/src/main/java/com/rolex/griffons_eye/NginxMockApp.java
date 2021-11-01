/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
public class NginxMockApp {
    public static void main(String[] args) {
        SpringApplication.run(NginxMockApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
