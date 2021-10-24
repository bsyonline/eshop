/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.eshop.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author rolex
 * @since 2021
 */
@Data
public class User {
    Long id;
    String name;
    LocalDateTime createTime;
}
