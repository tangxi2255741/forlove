package com.txr.forlove.common.redis;

import com.txr.forlove.common.constants.Separator;
import com.txr.forlove.common.utils.StringUtil;


public class RedisKeyPre {
    /** cbi应用公共前缀 */
    public static final String CBI_KEY_PRE = "cbi";
    /** task应用公共前缀 */
    public static final String TASK_KEY_PRE = "task";

    public static final String RULE_HIT = "ruleHit";

    private static final String ACCOUNT_DAY = "accountDay";

    public static final String REPAY_DAY = "repayDay";

    public static String addKeyPre(String app, String business, String key) {
        if (StringUtil.isBlank(app)
                || StringUtil.isBlank(business)
                || StringUtil.isBlank(key)) {
            throw new RuntimeException("缓存key的组成元素不允许为空");
        }
        StringBuilder builder = new StringBuilder(32);
        builder.append(app).append(Separator.REDIS_KEY_LINK).append(business).append(Separator.REDIS_KEY_LINK).append(key);
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
                builder.append(Separator.REDIS_KEY_LINK);
            }
        }
        return builder.toString();
    }

    public static String getRuleCacheKey(String channel, String skuId) {
        return buildKey(TASK_KEY_PRE, RULE_HIT, channel, skuId);
    }

    public static String getAccountDayKey(int accountDay) {
        return buildKey(TASK_KEY_PRE, ACCOUNT_DAY, String.valueOf(accountDay));
    }

    public static String getRepayDayKey(int accountDay) {
        return buildKey(TASK_KEY_PRE, REPAY_DAY, String.valueOf(accountDay));
    }

}
