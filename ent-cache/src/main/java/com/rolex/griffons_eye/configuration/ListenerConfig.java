/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.configuration;

import com.rolex.griffons_eye.listener.QueueInitListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rolex
 * @since 2021
 */
@Configuration
public class ListenerConfig {
    @Bean
    public ServletListenerRegistrationBean servletRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new QueueInitListener());
        return servletListenerRegistrationBean;
    }
}
