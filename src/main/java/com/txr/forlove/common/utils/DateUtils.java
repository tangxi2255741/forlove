package com.txr.forlove.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cdtangxi on 2017/8/22.
 */
@Slf4j
public class DateUtils {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 年-月-日 小时：分：秒
    public static final String DATE_FORMAT = "yyyy-MM-dd";// 年-月-日
    public static final String DATE_CH_FORMAT = "yyyy年MM月dd日";// yyyy年MM月dd日
    public static final String HHMM_FORMAT = "HH:mm";// 小时:分
    public static final String HHMMSS_FORMAT = "HH:mm:ss";// 小时:分:秒
    public static final String DATE_FORMAT_NEW = "yyyyMMdd";// 年月日数值

    /**
     * 返回某一天最后时刻的日期
     *
     * @param date
     * @return
     */
    public static Date toLastDateTime(Date date) {
        if (date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 返回某一天最后时刻的日期
     *
     * @param date
     * @return
     */
    public static Date toBeginDateTime(Date date) {
        if (date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null)
        {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            log.warn("日期格式化失败.{}", e.getMessage());
        }
        return null;
    }


    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DATE_TIME_FORMAT);
    }

    /**
     * 时间格式化， 传入毫秒
     *
     * @param time
     * @return
     */
    public static String dateFormat(long time) {
        return format(new Date(time), "yyyy-MM-dd HH:mm:ss");
    }

    public static Date format(String dateString){
        if(StringUtils.isEmpty(dateString)){
            return null;
        }

        return DateTime.parse(dateString, DateTimeFormat.forPattern(DateUtils.DATE_TIME_FORMAT)).toDate();
    }

    public static Date parseFromStr(String date) {
        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error("时间转换异常", e);
        }
        return null;
    }

    public static boolean validTimeInterval(Date beginTime, Date endTime) {
        if (endTime.getTime() > beginTime.getTime()){
            return true;
        }
        return false;
    }

    public static Date addTime(Date srcDate, int filed, int mount) {
        if (srcDate == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(srcDate);
        calendar.add(filed, mount);
        return calendar.getTime();
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {
    }
}
