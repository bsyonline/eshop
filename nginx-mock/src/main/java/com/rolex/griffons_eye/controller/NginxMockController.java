/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class NginxMockController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/ent_info")
    public String entInfoMock(String entId){
        String url = "http://localhost:8001/getEntInfo?entId=" + entId;
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if(exchange.getStatusCodeValue() == 200){
            return exchange.getBody();
        }
        return "{\"entId\":\"-1\", \"entName\":\"NONE\"}";
    }
}
