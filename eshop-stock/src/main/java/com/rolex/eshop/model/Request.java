/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.model;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 请求接口对象
 *
 * @author rolex
 * @since 2021
 */
public interface Request {

    Long getId();
    void process() throws JsonProcessingException;

}
