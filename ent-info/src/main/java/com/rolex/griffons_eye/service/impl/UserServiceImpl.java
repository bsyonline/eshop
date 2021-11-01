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
import com.rolex.griffons_eye.dao.mapper.UserMapper;
import com.rolex.griffons_eye.model.User;
import com.rolex.griffons_eye.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String PREFIX = "user:";
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisDao redisDao;

    @Override
    public void save(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String s = mapper.writeValueAsString(user);
        log.info(s);
        redisDao.set(PREFIX + user.getId(), s);
        userMapper.save(user);
    }

    @Override
    public User findById(Long id) throws JsonProcessingException {
        String s = redisDao.get(PREFIX + id);
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        User user = null;
        if (StringUtils.isEmpty(s)) {
            user = userMapper.findById(id);
            String value = mapper.writeValueAsString(user);
            log.info(value);
            redisDao.set(PREFIX + id, value);
        } else {
            log.info(s);
            user = mapper.readValue(s, User.class);
        }
        return user;
    }

}
