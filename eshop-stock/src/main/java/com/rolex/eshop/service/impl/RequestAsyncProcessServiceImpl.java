/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.service.impl;

import com.rolex.eshop.model.Request;
import com.rolex.eshop.service.RequestAsyncProcessService;
import com.rolex.eshop.threadpool.RequestQueue;
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
            BlockingQueue<Request> queue = routeQueue(request.getId());
            queue.put(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BlockingQueue<Request> routeQueue(Long id) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        /*
            对id求hash
         */
        String key = String.valueOf(id);
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
