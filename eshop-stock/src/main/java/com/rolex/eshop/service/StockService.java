package com.rolex.eshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.eshop.model.Stock;

/**
 * @author rolex
 * @since 2021
 */
public interface StockService {

    void updateStock(Stock stock);

    void removeStockCache(Stock stock);

    Stock findById(Long id);

    void setStockCache(Stock stock) throws JsonProcessingException;

    Stock findStockCache(Long id) throws JsonProcessingException;
}
