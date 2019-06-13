package com.txr.forlove.demo.java;

import com.txr.forlove.common.utils.MessageFormats;

import java.time.*;

public class JavaTimeDemo {

    public static void main(String[] args) {
        // 当前详细时间
        LocalDateTime currentTime = LocalDateTime.now(); //currentTime =  2019-05-30T15:05:46.408
        System.out.println(MessageFormats.format("当前详细时间：{}",currentTime));
        // 当前年月日
        LocalDate date1 = currentTime.toLocalDate(); //date1 = 2019-05-30
        System.out.println(MessageFormats.format("当前年月日：{}",date1));
        //获取详细时间的月日秒
        Month month = currentTime.getMonth();//month=MAY
        int day = currentTime.getDayOfMonth();//day=30
        int seconds = currentTime.getSecond();//seconds=46
        System.out.println(MessageFormats.format("month = {},day={},seconds={}",month,day,seconds));
        //替换详细时间的年月
        LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);//date2 = 2012-05-10T15:05:46.408
        System.out.println(MessageFormats.format("替换详细时间的年月：{}",date2));
        //自定义年月日
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);//date3= 2014-12-12
        System.out.println(MessageFormats.format("自定义年月日：{}",date3));
        //自定义时分
        LocalTime date4 = LocalTime.of(22, 15); //date4 = 22:15
        System.out.println(MessageFormats.format("自定义时分：{}",date4));
        //解析字符串
        LocalTime date5 = LocalTime.parse("20:15:30"); //date5 = 20:15:30
        LocalDateTime date6 = LocalDateTime.parse("2019-05-30T15:05:46.408");//date6 = 2019-05-30T15:05:46.408
        // 获取当前时间日期
        ZonedDateTime date7 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");//date6=2015-12-03T10:15:30+08:00[Asia/Shanghai]
        // 获取时区ID
        ZoneId id = ZoneId.of("Europe/Paris");//id= Europe/Paris
        //获取默认时区
        ZoneId defaultZone = ZoneId.systemDefault();//defaultZone=sia/Shanghai
    }
}
