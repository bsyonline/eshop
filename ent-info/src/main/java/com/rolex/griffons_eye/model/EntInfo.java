/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author rolex
 * @since 2021
 */
@Data
@Builder
public class EntInfo {
    String entId;
    String entName;
    LocalDateTime createTime;
    LocalDateTime modifiedTime;
}
