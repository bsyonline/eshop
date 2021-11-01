package com.rolex.griffons_eye.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.griffons_eye.model.EntInfo;

/**
 * @author rolex
 * @since 2021
 */
public interface EntInfoService {

    /**
     * 更新企业信息
     *
     * @param entInfo
     */
    void updateEntInfo(EntInfo entInfo);

    /**
     * 根据id查企业信息
     *
     * @param entId
     * @return
     */
    EntInfo findById(String entId);

    /**
     * 删除redis中的企业信息的缓存
     *
     * @param entInfo
     */
    void removeEntInfoFromRedis(EntInfo entInfo);


    /**
     * 设置redis的企业信息的缓存
     *
     * @param entInfo
     * @throws JsonProcessingException
     */
    void setEntInfo2Redis(EntInfo entInfo) throws JsonProcessingException;

    /**
     * 获取redis中的企业信息的缓存
     *
     * @param entId
     * @return
     * @throws JsonProcessingException
     */
    EntInfo getEntInfoFromRedis(String entId) throws JsonProcessingException;
}
