package com.rolex.griffons_eye.hystrix.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.rolex.griffons_eye.dao.RedisDao;
import com.rolex.griffons_eye.model.EntInfo;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.rolex.griffons_eye.service.impl.CacheServiceImpl.PREFIX;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class SaveEntInfo2RedisCommand extends HystrixCommand<Boolean> {

    EntInfo entInfo;
    RedisDao redisDao;

    public SaveEntInfo2RedisCommand(RedisDao redisDao, EntInfo entInfo) {
        super(HystrixCommandGroupKey.Factory.asKey("RedisGroup"));
        this.entInfo = entInfo;
        this.redisDao = redisDao;
    }

    @Override
    protected Boolean run() throws Exception {
        String key = PREFIX + entInfo.getEntId();
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String value = mapper.writeValueAsString(entInfo);
        log.info("ent info json : {}", value);
        redisDao.set(key, value);
        return true;
    }
}
