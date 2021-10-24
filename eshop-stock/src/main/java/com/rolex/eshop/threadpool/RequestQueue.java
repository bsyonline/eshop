/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.threadpool;

import com.rolex.eshop.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author rolex
 * @since 2021
 */
public class RequestQueue {

    /*
        内存队列列表
     */
    List<BlockingQueue<Request>> queues = new ArrayList<>();

    private static class Singleton {
        private static RequestQueue queue;

        static {
            queue = new RequestQueue();
        }

        static RequestQueue getInstance() {
            return queue;
        }
    }

    public static RequestQueue getInstance() {
        return RequestQueue.Singleton.getInstance();
    }

    public void addQueue(BlockingQueue<Request> queue){
        queues.add(queue);
    }

    public BlockingQueue<Request> getQueue(int index){
        return queues.get(index);
    }

    public int size(){
        return queues.size();
    }
}
