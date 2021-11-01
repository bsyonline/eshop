/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.dao;

/**
 * @author rolex
 * @since 2021
 */
public interface RedisDao {

    void set(String key, String value);

    String get(String key);

    void del(String key);

}
