/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop;

import com.rolex.eshop.listener.QueueInitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;

/**
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
public class EshopStockApp {
    public static void main(String[] args) {
        SpringApplication.run(EshopStockApp.class, args);
    }

    @Bean
    public ServletListenerRegistrationBean servletRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new QueueInitListener());
        return servletListenerRegistrationBean;
    }
}
