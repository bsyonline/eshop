/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.service;

import com.rolex.eshop.model.ProductInfo;

/**
 * @author rolex
 * @since 2021
 */
public interface CacheService {

    ProductInfo saveLocalCache(ProductInfo productInfo);

    ProductInfo getLocalCache(Long id);
}
