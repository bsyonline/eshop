/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.griffons_eye.configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rolex
 * @since 2020
 */
@Configuration
@MapperScan("com.rolex.eshop.dao.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.setInterceptors(Lists.newArrayList(new PaginationInnerInterceptor()));
        return mybatisPlusInterceptor;
    }

    @Bean
    public MetaObjectHandler objectHandler() {
        return new MyMetaObjectHandler();
    }
}
