/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
@Component
public class EntInfoChangedEventConsumer {

    @Autowired
    CuratorFramework client;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CacheService cacheService;

    @KafkaListener(groupId = "group01", topics = "#{'${spring.kafka.topic}'}")
    public void receive(ConsumerRecord<String, Object> record, Acknowledgment ack) throws JsonProcessingException {
        log.info("收到消息：{}", record.value());
        // 首先将message转换成json对象
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(String.valueOf(record.value()));
        // 从这里提取出消息对应的服务的标识
        String serviceId = jsonNode.path("serviceId").asText();
        // 如果是企业信息服务
        if ("ent-info".equals(serviceId)) {
            processEntInfoChangedMessage(jsonNode);
        }
        ack.acknowledge();
        log.info("ack={}", ack.toString());
    }

    private void processEntInfoChangedMessage(JsonNode jsonNode) throws JsonProcessingException {
        // 提取出企业id
        String entId = jsonNode.path("entId").asText();
        // 从企业信息服务获取最新的企业信息
        EntInfo entInfo = getEntInfo(entId);

        // 获取分布式锁
        String lockPath = "/ent-lock-" + entId;
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        try {
            lock.acquire();
            // 先从redis中获取数据
            EntInfo entInfoFromRedisCache = cacheService.getEntInfoFromRedisCache(entId);
            if (entInfoFromRedisCache != null) {
                // 比较当前数据的时间版本比已有数据的时间版本是新还是旧
                Date date = entInfo.getModifiedTime();
                Date existedDate = entInfoFromRedisCache.getModifiedTime();

                if (date.before(existedDate)) {
                    log.info("current date[{}] is before existed date[{}]", entInfo.getModifiedTime(), entInfoFromRedisCache.getModifiedTime());
                    return;
                }
                log.info("current date[{}] is after existed date[{}]", entInfo.getModifiedTime(), entInfoFromRedisCache.getModifiedTime());
            } else {
                log.info("existed ent info is null");
            }
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 将企业信息放到ehcache缓存
            cacheService.saveEntInfo2LocalCache(entInfo);
            log.info("save ent info to local cache");
            // 将企业信息放到redis缓存
            cacheService.saveEntInfo2RedisCache(entInfo);
            log.info("save ent info to redis cache");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放分布式锁
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private EntInfo getEntInfo(String entId) throws JsonProcessingException {
        String url = "http://localhost:8003/getEntInfo?entId=" + entId;
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (exchange.getStatusCodeValue() == 200) {
            String json = exchange.getBody();
            ObjectMapper mapper = new ObjectMapper();
            EntInfo entInfo = mapper.readValue(json, EntInfo.class);
            return entInfo;
        }
        return null;
    }
}
