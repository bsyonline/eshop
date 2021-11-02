/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.model;

import lombok.Data;

import java.util.Date;

/**
 * @author rolex
 * @since 2021
 */
@Data
public class EntInfo {
    Long id;
    String entId;
    String entName;
    Date modifiedTime;
}
