package com.txr.forlove.common.utils;

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
public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 年-月-日 小时：分：秒
    public static final String DATE_FORMAT = "yyyy-MM-dd";// 年-月-日
    public static final String DATE_CH_FORMAT = "yyyy年MM月dd日";// yyyy年MM月dd日
    public static final String HHMM_FORMAT = "HH:mm";// 小时:分
    public static final String HHMMSS_FORMAT = "HH:mm:ss";// 小时:分:秒
    public static final String DATE_FORMAT_NEW = "yyyyMMdd";// 年月日数值
    public static final String MAX_DATE = "2099-12-30 00:00:00";
    public static final String MIN_DATE = "2000-00-00 00:00:00";

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
            LOGGER.warn("日期格式化失败.{}", e.getMessage());
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

    public static Date maxDate() {
        return parseFromStr(MAX_DATE);
    }

    public static Date minDate() {
        return parseFromStr(MIN_DATE);
    }

    public static Date parseFromStr(String date) {
        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            LOGGER.error("时间转换异常", e);
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

    public static Date getRepayDate(Integer repayDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (currentDay > repayDate) {
            calendar.add(Calendar.MONTH, 1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, repayDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return toBeginDateTime(calendar.getTime());
    }

    public static Date getAccountDayBegin(int accountDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, accountDay);
        calendar.add(Calendar.MONTH, -1);
        return toBeginDateTime(calendar.getTime());
    }

    public static Date getAccountDayEnd(int accountDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, accountDay);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return toLastDateTime(calendar.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getAccountDayBegin(18));
        System.out.println(getAccountDayEnd(18));
    }
}
