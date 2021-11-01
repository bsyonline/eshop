/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.griffons_eye.service.EntInfoService;

/**
 * @author rolex
 * @since 2021
 */
public class EntInfoCacheRefreshRequest implements Request {

    String entId;
    EntInfoService entInfoService;

    public EntInfoCacheRefreshRequest(String entId, EntInfoService entInfoService) {
        this.entId = entId;
        this.entInfoService = entInfoService;
    }

    @Override
    public String getEntId() {
        return entId;
    }

    @Override
    public void process() throws JsonProcessingException {
        //从数据库中查询最新的库存
        EntInfo entInfo = entInfoService.findById(entId);
        //将库存放到缓存中
        entInfoService.setEntInfo2Redis(entInfo);
    }
}
