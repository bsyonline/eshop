/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.threadpool;

import com.rolex.griffons_eye.model.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author rolex
 * @since 2021
 */
public class RequestProcessThreadPool {

    /*
        线程池
     */
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public RequestProcessThreadPool() {
        RequestQueue requestQueue = new RequestQueue();
        for (int i = 0; i < 10; i++) {
            /*
                创建内存队列放到
             */
            BlockingQueue<Request> queue = new ArrayBlockingQueue(100);
            requestQueue.addQueue(queue);
            /*
                将工作线程和内存队列绑定
             */
            executorService.submit(new WorkThread(queue));
        }
    }

    private static class Singleton {
        private static RequestProcessThreadPool pool;

        static {
            pool = new RequestProcessThreadPool();
        }

        static RequestProcessThreadPool getInstance() {
            return pool;
        }
    }

    public static RequestProcessThreadPool getInstance() {
        return Singleton.getInstance();
    }

    public static void init() {
        getInstance();
    }
}
