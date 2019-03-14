package com.txr.forlove.common.utils;


import com.txr.forlove.common.enums.DescType;
import com.txr.forlove.common.enums.IntegerType;
import com.txr.forlove.common.enums.StringType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: T.X
 * @create: 2018-12-29 15:08
 **/
public class EnumMapUtils {
    private static final Map<Class<?>, Map<Integer, String>> INTEGER_KEY_MAP_HOLDER = new HashMap<Class<?>, Map<Integer, String>>();
    private static final Map<Class<?>, Map<String, String>> NAME_KEY_MAP_HOLDER = new HashMap<Class<?>, Map<String, String>>();
    private static final Map<Class<?>, Map<String, String>> STRING_KEY_MAP_HOLDER = new HashMap<Class<?>, Map<String, String>>();

    public static <T extends DescType> Map<Integer, String> toIntegerKeyMap(Class<T> enumClazz) {
        Map<Integer, String> map = INTEGER_KEY_MAP_HOLDER.get(enumClazz);

        if (map == null) {
            map = new LinkedHashMap<Integer, String>();
            for (T e : enumClazz.getEnumConstants()) {
                map.put(((IntegerType) e).getType(), e.getDesc());
            }
            INTEGER_KEY_MAP_HOLDER.put(enumClazz, map);
        }
        return map;
    }

    public static <T extends DescType> Map<String, String> toNameKeyMap(Class<T> enumClazz) {
        Map<String, String> map = NAME_KEY_MAP_HOLDER.get(enumClazz);

        if (map == null) {
            map = new LinkedHashMap<String, String>();
            for (T e : enumClazz.getEnumConstants()) {
                map.put(((Enum<?>) e).name(), e.getDesc());
            }
            NAME_KEY_MAP_HOLDER.put(enumClazz, map);
        }
        return map;
    }

    public static <T extends DescType> Map<String, String> toStringKeyMap(Class<T> enumClazz) {
        Map<String, String> map = STRING_KEY_MAP_HOLDER.get(enumClazz);

        if (map == null) {
            map = new LinkedHashMap<String, String>();
            for (T e : enumClazz.getEnumConstants()) {
                map.put(((StringType) e).getType(), e.getDesc());
            }
            STRING_KEY_MAP_HOLDER.put(enumClazz, map);
        }
        return map;
    }
}
