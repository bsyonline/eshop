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
import com.rolex.griffons_eye.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
public class QueryEntInfoFromRedisCommand extends HystrixCommand<EntInfo> {

    String entId;
    RedisDao redisDao;

    public QueryEntInfoFromRedisCommand(RedisDao redisDao, String entId) {
        super(HystrixCommandGroupKey.Factory.asKey("RedisGroup"));
        this.entId = entId;
        this.redisDao = redisDao;
    }

    @Override
    protected EntInfo run() throws Exception {
        String key = PREFIX + entId;
        String s = redisDao.get(key);
        log.info("ent info json : {}", s);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(s, EntInfo.class);
    }
}
