package com.txr.forlove.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Created by lihao on 2016/8/25.
 */
public class UUIDUtils {

    public static final String UUIDToken = "UUID";

    public static boolean resetAndGetUUID() {
        // 已有日志uuid则不重置
        if(StringUtils.isNotBlank(MDC.get(UUIDToken))){
            return false;
        }
        MDC.remove(UUIDToken);
        String uuidValue = UUID.randomUUID().toString();
        MDC.put(UUIDToken, uuidValue);
        return true;
    }


    public static void clearStart(){
        MDC.clear();
    }

    public static void clearUuid(boolean hasReset){
        if(hasReset){
            clearStart();
        }
    }

    public static String newUuid(){
        char[] chars = UUID.randomUUID().toString().toCharArray();
        int i = 0;
        for(int j = 0, len = chars.length; j < len; j++){
            if(chars[j] == '-'){
                continue;
            }
            chars[i] = chars[j];
            i++;
        }
        return String.valueOf(chars, 0, i);
    }

    /**
     * @description： UUID去掉-
     * @param：
     * @return：
    **/
    public static String getSimpleUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
