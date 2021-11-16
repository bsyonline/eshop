/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.rolex.griffons_eye.dao.RedisDao;
import com.rolex.griffons_eye.hystrix.command.QueryEntInfoFromRedisCommand;
import com.rolex.griffons_eye.hystrix.command.SaveEntInfo2RedisCommand;
import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author rolex
 * @since 2021
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
    private static final String CACHE_NAME = "local";
    public static final String PREFIX = "ent:info:";

    @Autowired
    RedisDao redisDao;

    /**
     * 将商品信息保存到本地的ehcache缓存中
     * @param entInfo
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "'key_' + #entInfo.getEntId()")
    public EntInfo saveEntInfo2LocalCache(EntInfo entInfo) {
        return entInfo;
    }

    /**
     * 从本地ehcache缓存中获取商品信息
     * @param entId
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "'key_'+#entId")
    public EntInfo getProductInfoFromLocalCache(String entId) {
        return null;
    }

    /**
     * 从redis中获取商品信息
     * @param entId
     */
    @Override
    public EntInfo getEntInfoFromRedisCache(String entId) {
        QueryEntInfoFromRedisCommand queryEntInfoFromRedisCommand = new QueryEntInfoFromRedisCommand(redisDao, entId);
        return queryEntInfoFromRedisCommand.execute();
    }

    /**
     * 将商品信息保存到redis中
     * @param entInfo
     */
    @Override
    public void saveEntInfo2RedisCache(EntInfo entInfo) throws JsonProcessingException {
        SaveEntInfo2RedisCommand saveEntInfo2RedisCommand = new SaveEntInfo2RedisCommand(redisDao, entInfo);
        saveEntInfo2RedisCommand.execute();
    }
}
