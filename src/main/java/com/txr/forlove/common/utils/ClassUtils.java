package com.txr.forlove.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by cdyuhuan on 2016/12/6.
 */
public class ClassUtils {

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        if (clazz == null || StringUtils.isBlank(methodName))
        {
            return null;
        }
        try {
            return clazz.getMethod(methodName, paramTypes);
        }
        catch (NoSuchMethodException ex) {
            throw new IllegalStateException("Expected method not found: " + ex);
        }
    }
}
