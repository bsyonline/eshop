/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
@Component
public class EntInfoChangedEventConsumer {
    @KafkaListener(groupId = "group01", topics = "#{'${spring.kafka.topic}'}")
    public void receive(ConsumerRecord<String, Object> record, Acknowledgment ack) throws JsonProcessingException {
        log.info("收到消息：{}", record.value());
        // 首先将message转换成json对象
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(String.valueOf(record.value()));
        // 从这里提取出消息对应的服务的标识
        String serviceId = jsonNode.path("serviceId").asText();
        // 如果是企业信息服务
        if("ent-info".equals(serviceId)) {
            processEntInfoChangedMessage(jsonNode);
        }
        ack.acknowledge();
        log.info("ack={}", ack.toString());
    }

    private void processEntInfoChangedMessage(JsonNode jsonNode) {
        // 提取出企业id
        String entId = jsonNode.path("entId").asText();

    }
}
