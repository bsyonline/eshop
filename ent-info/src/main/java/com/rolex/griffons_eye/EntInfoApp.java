/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye;

import com.rolex.griffons_eye.listener.QueueInitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
public class EntInfoApp {
    public static void main(String[] args) {
        SpringApplication.run(EntInfoApp.class, args);
    }

    @Bean
    public ServletListenerRegistrationBean servletRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new QueueInitListener());
        return servletListenerRegistrationBean;
    }
}
