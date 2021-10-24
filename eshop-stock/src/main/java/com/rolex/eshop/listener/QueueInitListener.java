/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.listener;

import com.rolex.eshop.threadpool.RequestProcessThreadPool;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
public class QueueInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        RequestProcessThreadPool.init();
    }
}
