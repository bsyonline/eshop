/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.queue;

import com.rolex.griffons_eye.model.EntInfo;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author rolex
 * @since 2021
 */
public class RebuildCacheQueue {

    private ArrayBlockingQueue<EntInfo> queue = new ArrayBlockingQueue<>(1000);

    public void putEntInfo(EntInfo entInfo) {
        try {
            queue.put(entInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EntInfo takeEntInfo() {
        try {
            return queue.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Singleton {

        private static RebuildCacheQueue instance;

        static {
            instance = new RebuildCacheQueue();
        }

        public static RebuildCacheQueue getInstance() {
            return instance;
        }

    }

    public static RebuildCacheQueue getInstance() {
        return Singleton.getInstance();
    }

    public static void init() {
        getInstance();
    }
}
