/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.service.impl;

import com.rolex.griffons_eye.model.Request;
import com.rolex.griffons_eye.service.RequestAsyncProcessService;
import com.rolex.griffons_eye.threadpool.RequestQueue;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

/**
 * 异步处理service
 *
 * @author rolex
 * @since 2021
 */
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Override
    public void process(Request request) {
        try {
            /*
                请求路由，根据每个商品的id路由到对应的队列中
             */
            BlockingQueue<Request> queue = routeQueue(request.getEntId());
            queue.put(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BlockingQueue<Request> routeQueue(String entId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        /*
            对id求hash
         */
        String key = entId;
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        /*
            对hash取模
         */
        int index = (requestQueue.size() - 1) & hash;
        /*
            获取对应的queue
         */
        return requestQueue.getQueue(index);
    }
}
