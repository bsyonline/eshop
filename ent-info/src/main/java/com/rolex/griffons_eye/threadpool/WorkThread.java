/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.threadpool;

import com.rolex.griffons_eye.model.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * 工作线程
 *
 * @author rolex
 * @since 2021
 */
public class WorkThread implements Callable<Boolean> {

    /*
        对应的内存队列
     */
    BlockingQueue<Request> queue;

    public WorkThread(BlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try{
            while(true){
                Request request = queue.take();
                request.process();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
