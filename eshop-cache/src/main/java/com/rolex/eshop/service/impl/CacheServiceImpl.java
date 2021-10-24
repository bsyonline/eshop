/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.service.impl;

import com.rolex.eshop.model.ProductInfo;
import com.rolex.eshop.service.CacheService;
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

    @Override
    @CachePut(value = CACHE_NAME, key = "'key_' + #productInfo.getId()")
    public ProductInfo saveLocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'key_'+#id")
    public ProductInfo getLocalCache(Long id) {
        return null;
    }
}
