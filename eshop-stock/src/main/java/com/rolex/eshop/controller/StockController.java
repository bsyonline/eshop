/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.controller;

import com.rolex.eshop.model.Stock;
import com.rolex.eshop.model.StockCacheRefreshRequest;
import com.rolex.eshop.model.StockUpdateRequest;
import com.rolex.eshop.service.RequestAsyncProcessService;
import com.rolex.eshop.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2021
 */
@RestController
@Slf4j
public class StockController {

    @Autowired
    StockService stockService;
    @Autowired
    RequestAsyncProcessService requestAsyncProcessService;

    @RequestMapping("/updateStock")
    public String updateStock(Stock stock) {
        log.info("");
        try {
            StockUpdateRequest stockUpdateRequest = new StockUpdateRequest(stock, stockService);
            requestAsyncProcessService.process(stockUpdateRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @GetMapping("/getStock")
    public Stock getStock(Long id) {
        try {
            StockCacheRefreshRequest stockCacheRefreshRequest = new StockCacheRefreshRequest(id, stockService);
            requestAsyncProcessService.process(stockCacheRefreshRequest);

            long startTime = System.currentTimeMillis();
            long waitTime = 0L;

            while (true) {
                if (waitTime > 200) {
                    break;
                }

                Stock stockCache = stockService.findStockCache(id);
                if (stockCache != null) {
                    return stockCache;
                }

                Thread.sleep(20);
                waitTime = System.currentTimeMillis() - startTime;
            }

            Stock stock = stockService.findById(id);
            if (stock != null) {
                stockService.setStockCache(stock);
                return stock;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
