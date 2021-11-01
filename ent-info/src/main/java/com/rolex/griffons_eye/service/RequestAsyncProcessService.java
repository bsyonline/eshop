package com.rolex.griffons_eye.service;

import com.rolex.griffons_eye.model.Request;

/**
 * @author rolex
 * @since 2021
 */
public interface RequestAsyncProcessService {
    void process(Request request);
}
