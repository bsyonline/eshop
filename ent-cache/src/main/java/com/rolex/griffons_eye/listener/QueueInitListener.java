/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.listener;

import com.rolex.griffons_eye.queue.RebuildCacheQueue;
import com.rolex.griffons_eye.queue.RebuildCacheThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author rolex
 * @since 2021
 */
public class QueueInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        RebuildCacheQueue.init();
    }
}
