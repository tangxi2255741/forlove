package com.txr.forlove.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class MyCollectionUtils {

    /**
     * @description：对象转换为集合
     * @param：
     * @return：
    **/
    public static <T> List<T> transToSingleList(T item) {
        if (item == null){
            return null;
        }
        if (item instanceof String){
            String str = (String)item;
            if (StringUtils.isBlank(str)){
                return null;
            }
        }
        List<T> list = new ArrayList<T>();
        list.add(item);
        return list;
    }

    /**
     * @description：对象转换为set集合
     * @param：
     * @return：
    **/
    public static <T> Set<T> transToSingleSet(T item) {
        Set<T> set = new HashSet<T>();
        set.add(item);
        return set;
    }

    public static List<String> transToStr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        str = handleStr(str);
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

    /**
     * @description：处理字符串的前后空格：222, 333, 444 ,555 - > 222,333,444,555
     * @param：
     * @return：
    **/
    public static String handleStr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return str.trim().replaceAll("(,)\\s+", "$1").replaceAll("\\s+(,)", "$1");
    }

    public static void main(String[] args) {
        String s = "222,333, 444 ,555";
        System.out.println(transToStr(s));
        System.out.println(handleStr(s));
    }
}
