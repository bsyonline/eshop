/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.controller;

import com.rolex.griffons_eye.model.EntInfo;
import com.rolex.griffons_eye.model.EntInfoCacheRefreshRequest;
import com.rolex.griffons_eye.model.EntInfoUpdateRequest;
import com.rolex.griffons_eye.service.RequestAsyncProcessService;
import com.rolex.griffons_eye.service.EntInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author rolex
 * @since 2021
 */
@RestController
@Slf4j
public class EntInfoController {

    @Autowired
    EntInfoService entInfoService;
    @Autowired
    RequestAsyncProcessService requestAsyncProcessService;

    @RequestMapping("/updateEntInfo")
    public String updateEntInfo(EntInfo entInfo) {
        log.info("");
        try {
            EntInfoUpdateRequest entInfoUpdateRequest = new EntInfoUpdateRequest(entInfo, entInfoService);
            requestAsyncProcessService.process(entInfoUpdateRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @GetMapping("/getEntInfo")
    public EntInfo getEntInfo(String entId) {
        try {
            EntInfoCacheRefreshRequest entInfoCacheRefreshRequest = new EntInfoCacheRefreshRequest(entId, entInfoService);
            requestAsyncProcessService.process(entInfoCacheRefreshRequest);

            long startTime = System.currentTimeMillis();
            long waitTime = 0L;

            while (true) {
                if (waitTime > 200) {
                    break;
                }

                EntInfo entInfoCache = entInfoService.getEntInfoFromRedis(entId);
                if (entInfoCache != null) {
                    return entInfoCache;
                }

                Thread.sleep(20);
                waitTime = System.currentTimeMillis() - startTime;
            }

            EntInfo entInfo = entInfoService.findById(entId);
            if (entInfo != null) {
                entInfoService.setEntInfo2Redis(entInfo);
                return entInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return EntInfo.builder()
                .entId(entId)
                .modifiedTime(LocalDateTime.MIN)
                .build();
    }

}
