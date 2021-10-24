package com.rolex.eshop.dao.mapper;

import com.rolex.eshop.model.Stock;
import org.apache.ibatis.annotations.Param;

/**
 * @author rolex
 * @since 2021
 */
public interface StockMapper {

    void updateStock(@Param("stock") Stock stock);

    Stock findById(@Param("id") Long id);
}
