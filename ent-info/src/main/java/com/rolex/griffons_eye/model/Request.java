/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.model;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 请求接口对象
 *
 * @author rolex
 * @since 2021
 */
public interface Request {

    String getEntId();
    void process() throws JsonProcessingException;

}
