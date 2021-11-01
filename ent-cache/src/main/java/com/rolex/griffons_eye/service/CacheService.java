/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.griffons_eye.model.EntInfo;

/**
 * @author rolex
 * @since 2021
 */
public interface CacheService {

    /**
     * 将商品信息保存到本地的ehcache缓存中
     * @param entInfo
     */
    EntInfo saveProductInfo2LocalCache(EntInfo entInfo);

    /**
     * 从本地ehcache缓存中获取商品信息
     * @param entId
     * @return
     */
    EntInfo getProductInfoFromLocalCache(String entId);

    /**
     * 从redis中获取商品信息
     * @param entId
     */
    EntInfo getProductInfoFromRedisCache(String entId) throws JsonProcessingException;

    /**
     * 将商品信息保存到redis中
     * @param entInfo
     */
    void saveProductInfo2RedisCache(EntInfo entInfo) throws JsonProcessingException;
}
