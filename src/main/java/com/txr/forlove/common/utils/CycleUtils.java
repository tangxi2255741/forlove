package com.txr.forlove.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: T.X
 * @create: 2019-01-03 10:40
 **/
public class CycleUtils {

    public static Date getNextCycle(Date srcTime, Integer cycle, Integer selfDefine) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(srcTime);
        switch (cycle) {
            case 1: // 每天
                clearClock(calendar);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                return calendar.getTime();
            case 2: // 每周
                clearClock(calendar);
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                return calendar.getTime();
            case 3:
                clearClock(calendar);
                calendar.add(Calendar.MONTH, 1);
                return calendar.getTime();
            case 4:
                return srcTime;
            case 5:
                return DateUtils.addTime(srcTime, Calendar.HOUR_OF_DAY, selfDefine);
                default :
                    return srcTime;
        }
    }

    private static void clearClock(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public static Date getNextCycleTimeFormNowOn(Date beginTime, Integer cycle, Integer customizeCycle) {
        Date now = new Date();
        Date nextCycleTime = beginTime;
        while (!DateUtils.validTimeInterval(now, nextCycleTime)) {
            nextCycleTime = getNextCycle(nextCycleTime, cycle, customizeCycle);
        }
        return nextCycleTime;
    }

}
