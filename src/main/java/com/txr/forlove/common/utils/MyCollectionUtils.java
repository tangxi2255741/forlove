package com.txr.forlove.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class MyCollectionUtils {

    public static <T> List<T> getSingleList(T item) {
        if (item == null)
        {
            return null;
        }

        if (item instanceof String)
        {
            String str = (String)item;
            if (StringUtils.isBlank(str))
            {
                return null;
            }
        }

        List<T> list = new ArrayList<T>();
        list.add(item);
        return list;
    }

    public static <T> Set<T> getSingleSet(T item) {
        Set<T> set = new HashSet<T>();
        set.add(item);
        return set;
    }

    public static List<String> transToStr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        str = str.trim().replaceAll("(,)\\s+", "$1").replaceAll("\\s+(,)", "$1");
        String[] split = str.split(",");
        return Arrays.asList(split);

    }

    public static List<Long> transToLong(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        str = str.replaceAll("\\s", "");
        String[] split = str.split(",");
        List<Long> idList = new ArrayList<Long>(split.length);
        for (String s : split) {
            idList.add(Long.valueOf(s));
        }
        return idList;
    }


    public static String handleStr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return str.trim().replaceAll("(,)\\s+", "$1").replaceAll("\\s+(,)", "$1");
    }

}
