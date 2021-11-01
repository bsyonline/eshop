/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.service.impl;

import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author rolex
 * @since 2021
 */
@Service
public class CacheServiceImpl implements CacheService {
    private static final String CACHE_NAME = "local";

    /**
     * 将商品信息保存到本地的ehcache缓存中
     * @param entInfo
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "'key_' + #productInfo.getId()")
    public EntInfo saveProductInfo2LocalCache(EntInfo entInfo) {
        return entInfo;
    }

    /**
     * 从本地ehcache缓存中获取商品信息
     * @param entId
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "'key_'+#id")
    public EntInfo getProductInfoFromLocalCache(String entId) {
        return null;
    }

    /**
     * 从redis中获取商品信息
     * @param entId
     */
    @Override
    public EntInfo getProductInfoFromRedisCache(String entId) {

        return null;
    }

    /**
     * 将商品信息保存到redis中
     * @param entInfo
     */
    @Override
    public void saveProductInfo2RedisCache(EntInfo entInfo) {

    }
}
