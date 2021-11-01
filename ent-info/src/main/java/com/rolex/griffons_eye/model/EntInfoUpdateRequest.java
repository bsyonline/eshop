/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.model;

import com.rolex.griffons_eye.service.EntInfoService;

/**
 * @author rolex
 * @since 2021
 */
public class EntInfoUpdateRequest implements Request {

    public EntInfoUpdateRequest(EntInfo entInfo, EntInfoService entInfoService) {
        this.entInfo = entInfo;
        this.entInfoService = entInfoService;
    }

    EntInfo entInfo;
    EntInfoService entInfoService;

    @Override
    public String getEntId() {
        return entInfo.getEntId();
    }

    @Override
    public void process() {
        //删除redis中的缓存
        entInfoService.removeEntInfoFromRedis(entInfo);
        //修改数据库中的库存
        entInfoService.updateEntInfo(entInfo);
    }
}
