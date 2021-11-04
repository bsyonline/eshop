/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.griffons_eye.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author rolex
 * @since 2021
 */
@Data
public class EntInfo implements Serializable {
    String entId;
    String entName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date modifiedTime;
}
