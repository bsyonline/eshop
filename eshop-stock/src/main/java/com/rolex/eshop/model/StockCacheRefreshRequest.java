/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.eshop.service.StockService;

/**
 * @author rolex
 * @since 2021
 */
public class StockCacheRefreshRequest implements Request {

    Long id;
    StockService stockService;

    public StockCacheRefreshRequest(Long id, StockService stockService) {
        this.id = id;
        this.stockService = stockService;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void process() throws JsonProcessingException {
        //从数据库中查询最新的库存
        Stock stock = stockService.findById(id);
        //将库存放到缓存中
        stockService.setStockCache(stock);
    }
}
