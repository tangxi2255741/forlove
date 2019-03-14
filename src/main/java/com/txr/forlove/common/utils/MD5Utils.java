package com.txr.forlove.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * MD5加密
 * 
 * @author cdduq 2013-10-9
 */
public final class MD5Utils {
    
    private MD5Utils() {
        
    }
    
    /**
     * MD5加密字符串，UTF-8编码
     * 
     * @return
     */
    public static final String useMD5(String origin) {
        if (StringUtils.isNotBlank(origin)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.reset();
                md.update(origin.getBytes("utf-8"));
                return toHexString(md.digest());
            } catch (Exception e) {
            }
        }
        return null;
    }
    
    /**
     * byte数组转换为16进制字符串
     * 
     * @param bytes
     * @return
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(0xFF & bytes[i]);
            if (temp.length() == 1) { // 高位补零
                str.append("0");
            }
            str.append(temp);
        }
        return str.toString();
    }
}
