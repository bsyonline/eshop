/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.model;

import com.rolex.eshop.service.StockService;

/**
 * @author rolex
 * @since 2021
 */
public class StockUpdateRequest implements Request {

    public StockUpdateRequest(Stock stock, StockService stockService) {
        this.stock = stock;
        this.stockService = stockService;
    }

    Stock stock;
    StockService stockService;

    @Override
    public Long getId() {
        return stock.getId();
    }

    @Override
    public void process() {
        //删除redis中的缓存
        stockService.removeStockCache(stock);
        //修改数据库中的库存
        stockService.updateStock(stock);
    }
}
