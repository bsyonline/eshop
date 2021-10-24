/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.rolex.eshop.dao.RedisDao;
import com.rolex.eshop.dao.mapper.StockMapper;
import com.rolex.eshop.model.Stock;
import com.rolex.eshop.service.StockService;
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
public class StockServiceImpl implements StockService {
    private static final String PREFIX = "product:stock:";
    @Autowired
    StockMapper stockMapper;
    @Autowired
    RedisDao redisDao;

    @Override
    public void updateStock(Stock stock) {
        stockMapper.updateStock(stock);
    }

    @Override
    public void removeStockCache(Stock stock) {
        String key = PREFIX + stock.getId();
        redisDao.del(key);
    }

    @Override
    public Stock findById(Long id) {
        return stockMapper.findById(id);
    }

    @Override
    public void setStockCache(Stock stock) throws JsonProcessingException {
        String key = PREFIX + stock.getId();
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        redisDao.set(key, mapper.writeValueAsString(stock));
    }

    @Override
    public Stock findStockCache(Long id) throws JsonProcessingException {
        String key = PREFIX  + id;
        String s = redisDao.get(key);
        if(StringUtils.isEmpty(s)){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(s, Stock.class);
    }


}
