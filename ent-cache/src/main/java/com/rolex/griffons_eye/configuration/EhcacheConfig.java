package com.rolex.griffons_eye.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@EnableCaching
@Configuration
public class EhcacheConfig {
    @Bean
    public CacheManager cacheManager(){
        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean ehCacheBean = new EhCacheManagerFactoryBean();
        ehCacheBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheBean.setShared(true);
        return ehCacheBean;
    }
}
