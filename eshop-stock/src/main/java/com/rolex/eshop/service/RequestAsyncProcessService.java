package com.rolex.eshop.service;

import com.rolex.eshop.model.Request;

/**
 * @author rolex
 * @since 2021
 */
public interface RequestAsyncProcessService {

    void process(Request request);
}
