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
import com.rolex.griffons_eye.dao.mapper.EntInfoMapper;
import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.service.EntInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author rolex
 * @since 2021
 */
@Service
public class EntInfoServiceImpl implements EntInfoService {
    private static final String PREFIX = "ent:info:";
    @Autowired
    EntInfoMapper entInfoMapper;
    @Autowired
    RedisDao redisDao;

    @Override
    public void updateEntInfo(EntInfo entInfo) {
        entInfoMapper.updateEntInfo(entInfo);
    }

    @Override
    public void removeEntInfoFromRedis(EntInfo entInfo) {
        String key = PREFIX + entInfo.getEntId();
        redisDao.del(key);
    }

    @Override
    public EntInfo findById(String entId) {
        return entInfoMapper.findById(entId);
    }

    @Override
    public void setEntInfo2Redis(EntInfo entInfo) throws JsonProcessingException {
        String key = PREFIX + entInfo.getEntId();
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        redisDao.set(key, mapper.writeValueAsString(entInfo));
    }

    @Override
    public EntInfo getEntInfoFromRedis(String entId) throws JsonProcessingException {
        String key = PREFIX + entId;
        String s = redisDao.get(key);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(s, EntInfo.class);
    }


}
