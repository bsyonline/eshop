/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.queue;

import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.service.CacheService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
@Component
public class RebuildCacheThread implements Runnable, DisposableBean {
    private Thread thread;
    @Autowired
    CacheService cacheService;
    @Autowired
    CuratorFramework client;

    public RebuildCacheThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    @SneakyThrows
    @Override
    public void run() {
        RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
        while (true) {
            EntInfo entInfo = rebuildCacheQueue.takeEntInfo();
            log.info("take a ent info from rebuild cache queue in rebuild thread, entId={}", entInfo.getEntId());
            // 获取分布式锁
            String lockPath = "/ent-lock-" + entInfo.getEntId();
            InterProcessMutex lock = new InterProcessMutex(client, lockPath);
            try {
                lock.acquire();
                log.info("acquire lock in rebuild thread, entId={}", entInfo.getEntId());
                EntInfo entInfoFromRedisCache = cacheService.getEntInfoFromRedisCache(entInfo.getEntId());
                if (entInfoFromRedisCache != null) {
                    // 比较当前数据的时间版本比已有数据的时间版本是新还是旧
                    try {
                        Date date = entInfo.getModifiedTime(); // 00:00:01
                        Date existedDate = entInfoFromRedisCache.getModifiedTime(); // 00:00:00
                        if (date.before(existedDate)) {
                            log.info("current date[{}] is before existed date[{}], entId={}", entInfo.getModifiedTime(), entInfoFromRedisCache.getModifiedTime(), entInfo.getEntId());
                            continue;
                        }
                        log.info("current date[{}] is after existed date[{}], entId={}", entInfo.getModifiedTime(), entInfoFromRedisCache.getModifiedTime(), entInfo.getEntId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    log.info("existed ent info is null, entId={}", entInfo.getEntId());
                }
                cacheService.saveEntInfo2LocalCache(entInfo);
                log.info("save ent info to local cache in rebuild thread, entId={}", entInfo.getEntId());
                cacheService.saveEntInfo2RedisCache(entInfo);
                log.info("save ent info to redis cache in rebuild thread, entId={}", entInfo.getEntId());
            } finally {
                try {
                    lock.release();
                    log.info("release lock in kafka consumer, entId={}", entInfo.getEntId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() throws Exception {

    }
}
