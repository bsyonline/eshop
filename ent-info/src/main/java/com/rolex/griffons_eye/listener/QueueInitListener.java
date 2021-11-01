/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.listener;

import com.rolex.griffons_eye.threadpool.RequestProcessThreadPool;
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
