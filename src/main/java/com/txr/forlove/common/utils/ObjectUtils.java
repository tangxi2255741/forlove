package com.txr.forlove.common.utils;

import com.txr.forlove.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author: T.X
 * @create: 2019-03-18 19:28
 **/
public class ObjectUtils {

    /**
     * 判断对象中属性值是否全为空
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                System.out.print(f.getName() + ":");
                System.out.println(f.get(object));
                // 有一个有值则返回
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        User user = new User();
        Boolean isAllNull = checkObjAllFieldsIsNull(user);
        System.out.println("set before : " + isAllNull);
        user.setId(2L);
        user.setName("tangxi");
        isAllNull = checkObjAllFieldsIsNull(user);
        System.out.println("set after : " + isAllNull);
    }
}
