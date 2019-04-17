package com.txr.forlove.common.utils;

import com.alibaba.fastjson.JSON;
import com.txr.forlove.common.constants.Separator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjunyong6 2018/12/25 14:04
 **/
public class SplitJointUtil {

    public static String list2String(List list) {
        if (CollectionUtils.isNotEmpty(list)) {
            StringBuilder builder = new StringBuilder(256);
            for (Object way : list) {
                builder.append(way);
                builder.append(Separator.COMMA);
            }
            builder.deleteCharAt(builder.lastIndexOf(Separator.COMMA));
            return builder.toString();
        }
        return null;
    }

    public static <T> List<T> string2List(String value, Class<T> clazz){
        if (StringUtils.isBlank(value)){
            return null;
        }
        String[] values = value.split(Separator.COMMA);
        List<T> list = new ArrayList<T>();
        for (String str: values) {
            list.add(JSON.parseObject(str, clazz));
        }
        return list;
    }

}
