/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.controller;

import com.rolex.eshop.model.ProductInfo;
import com.rolex.eshop.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class CacheController {

    @Autowired
    CacheService cacheService;

    @RequestMapping("/putCache")
    public String putCache(ProductInfo productInfo){
        cacheService.saveLocalCache(productInfo);
        return "success";
    }

    @GetMapping("/getCache")
    public ProductInfo getCache(Long id){
        return cacheService.getLocalCache(id);
    }
}
