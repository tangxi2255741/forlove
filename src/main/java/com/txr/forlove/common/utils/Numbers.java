package com.txr.forlove.common.utils;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * 
 * @since 2017年11月25日
 * @author yanglei
 *
 */
public abstract class Numbers {

    public static final BigDecimal _0_00_BigDecimal = new BigDecimal("0.00");

    public static Long toLong(String str, Long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static Integer toInteger(String str, Integer defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static int nullToZero(Integer value) {
        return value == null ? 0 : value;
    }

    public static boolean isGtZero(Integer value) {
        return value != null && value > 0;
    }

    public static boolean isGtZero(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) < 0;
    }
    
    public static boolean isGetZero(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) <= 0;
    }

    public static boolean isGtZero(Long value) {
        return value != null && value > 0;
    }

    public static boolean isGetZero(Integer value) {
        return value != null && value >= 0;
    }

    public static BigDecimal getAmount(BigDecimal value) {
        if(value == null){
            return value;
        }
        
        int digits = Currency.getInstance("CNY").getDefaultFractionDigits();
        return BigDecimal.valueOf(value.movePointRight(digits).setScale(0, 6).longValue(), digits);
        //return value.setScale(0, Currency.getInstance("CNY").getDefaultFractionDigits());
        //return BigDecimal.valueOf(value.longValue(), Currency.getInstance("CNY").getDefaultFractionDigits());
    }
}
