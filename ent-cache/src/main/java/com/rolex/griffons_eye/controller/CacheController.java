/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class CacheController {

    @Autowired
    CacheService cacheService;

    @GetMapping("/getEntInfo")
    public EntInfo entInfo(String entId) throws JsonProcessingException {
        EntInfo entInfo = null;

        entInfo = cacheService.getEntInfoFromRedisCache(entId);
        if (entInfo != null) {
            System.out.println("=================从redis中获取缓存，ent信息=" + entInfo);
        }

        if (entInfo == null) {
            entInfo = cacheService.getProductInfoFromLocalCache(entId);
            if (entInfo != null) {
                System.out.println("=================从ehcache中获取缓存，ent信息=" + entInfo);
            }
        }

        if(entInfo == null) {
            // 就需要从数据源重新拉取数据，重建缓存
//            GetProductInfoCommand command = new GetProductInfoCommand(productId);
//            productInfo = command.execute();

            // 将数据推送到一个内存队列中
//            RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
//            rebuildCacheQueue.putProductInfo(productInfo);
        }

        return entInfo;
    }
}
