package com.admin4j.commons.time;

import com.admin4j.common.time.LocalDateUtil;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * @author andanyang
 * @since 2022/4/26 17:11
 */
public class LocalDateUtilTest extends TestCase {

    public void testParse() {

        int rawOffset = TimeZone.getDefault().getRawOffset();
        System.out.println("rawOffset = " + rawOffset);
        String id = ZoneOffset.systemDefault().getId();
        System.out.println("id = " + id);
        ZoneOffset defaultZoomOffset = LocalDateUtil.DEFAULT_ZONE_OFFSET;
        System.out.println("defaultZoomOffset = " + defaultZoomOffset);

        //ZoneOffset of = ZoneOffset.of("UTC+8");
        ZoneOffset of1 = ZoneOffset.of("+8");
        //ZoneOffset of2 = ZoneOffset.of("8");
        ZoneOffset of3 = ZoneOffset.of("-8");
        //ZoneOffset of4 = ZoneOffset.of("UTC");
        ZoneOffset of5 = ZoneOffset.of("+0");
        System.out.println("of5 = " + of5);


        LocalDateTime parse = LocalDateUtil.parse("2015-01-03 00:12:13");
        System.out.println("parse = " + parse);

        Long aLong = LocalDateUtil.parse2EpochSecond("2015-01-03 00:12:13", "+8");
        System.out.println("aLong = " + aLong);
    }

    public void testParse2EpochSecond() {
    }
}