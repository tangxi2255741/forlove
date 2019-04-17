package com.txr.forlove.common.redis;

import com.txr.forlove.common.constants.Separator;
import com.txr.forlove.common.utils.StringUtil;
import org.springframework.util.Assert;

/**
 * 缓存Key工具类
 */
public class CacheKeyUtils {

    public static String addKeyPre(String app, String business, String key) {
        Assert.hasText(app,"应用名不能为空");
        Assert.hasText(business,"业务标识不能为空");
        Assert.hasText(key,"缓存key不能为空");
        StringBuilder builder = new StringBuilder(32);
        builder.append(app).append(Separator.UNDERLINE).append(business).append(Separator.UNDERLINE).append(key);
        return builder.toString();
    }

    public static String buildKey(String... keyElement) {
        StringBuilder builder = new StringBuilder();
        int length = keyElement.length;
        for (int i=0; i<length; i++) {
            if (StringUtil.isBlank(keyElement[i])) {
                throw new RuntimeException("缓存key的组成元素不允许为空");
            }
            builder.append(keyElement[i]);
            if (i + 1 != length) {
                builder.append(Separator.UNDERLINE);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String key = buildKey("tangxi","测试");
        System.out.println(key);
        System.out.println(addKeyPre("forlove","test",key));
    }
}
