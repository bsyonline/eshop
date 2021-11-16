package com.rolex.griffons_eye.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.rolex.griffons_eye.model.EntInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class QueryEntInfoCommand extends HystrixCommand<EntInfo> {

    String entId;

    public QueryEntInfoCommand(String entId) {
        super(HystrixCommandGroupKey.Factory.asKey("EntInfoService"));
        this.entId = entId;
    }

    @Override
    protected EntInfo run() throws Exception {
        EntInfo entInfo = new EntInfo();
        entInfo.setEntId("2");
        entInfo.setEntName("test");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse("2021-11-03 00:00:01");
            entInfo.setModifiedTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entInfo;
    }
}
