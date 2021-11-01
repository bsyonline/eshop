/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rolex.griffons_eye.model.User;

/**
 * @author rolex
 * @since 2021
 */
public interface UserService {
    void save(User user) throws JsonProcessingException;

    User findById(Long id) throws JsonProcessingException;
}
