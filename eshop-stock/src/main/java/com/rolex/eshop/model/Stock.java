/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author rolex
 * @since 2021
 */
@Data
public class Stock {
    Long id;
    String num;
    LocalDateTime createTime;
}
