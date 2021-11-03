/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.queue.RebuildCacheQueue;
import com.rolex.griffons_eye.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author rolex
 * @since 2021
 */
@RestController
@Slf4j
public class CacheController {

    @Autowired
    CacheService cacheService;

    @GetMapping("/getEntInfo")
    public EntInfo entInfo(String entId) throws JsonProcessingException {
        EntInfo entInfo = null;

        entInfo = cacheService.getEntInfoFromRedisCache(entId);
        if (entInfo != null) {
            log.info("get ent info from redis cache, entInfo={}", entInfo);
        }

        if (entInfo == null) {
            entInfo = cacheService.getProductInfoFromLocalCache(entId);
            if (entInfo != null) {
                log.info("get ent info from local cache, entInfo={}", entInfo);
            }
        }

        if(entInfo == null) {
            // 就需要从数据源重新拉取数据，重建缓存
//            GetEntInfoCommand command = new GetEntInfoCommand(entId);
//            entInfo = command.execute();

            entInfo = new EntInfo();
            SimpleDateFormat sdf = new SimpleDateFormat();
            try {
                Date date = sdf.parse("2021-11-03 00:00:01");
                entInfo.setModifiedTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            log.info("get ent info from ent service, entInfo={}", entInfo);

            // 将数据推送到一个内存队列中
            RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
            rebuildCacheQueue.putEntInfo(entInfo);
            log.info("put ent info into rebuild cache queue, entInfo={}", entInfo);
        }

        return entInfo;
    }
}
