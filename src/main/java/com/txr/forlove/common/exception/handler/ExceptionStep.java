package com.txr.forlove.common.exception.handler;


import com.txr.forlove.common.enums.DescType;
import com.txr.forlove.common.enums.Enums;
import com.txr.forlove.common.enums.IntegerType;

import java.util.Map;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public enum ExceptionStep implements IntegerType, DescType {
    validate_exception(10, "校验异常"),
    access_exception(20, "数据库/Redis访问异常"),
    rpc_exception(30, "RPC调用异常"),

    system_exception(40, "系统异常"),

    biz_exception(60, "业务异常"),
    app_exception(70, "应用异常"),

    throwable(Integer.MAX_VALUE, "未知", true), ;

    private static final Map<Integer, ExceptionStep> HOLDER = Enums.toMap(ExceptionStep.class.getEnumConstants());

    public static ExceptionStep valueOf(int state) {
        ExceptionStep value = HOLDER.get(state);
        return value == null ? throwable : value;
    }

    private final int step;
    private final boolean skipEnable;
    private final String desc;

    private ExceptionStep(int step, String desc, boolean skipEnable) {
        this.step = step;
        this.skipEnable = skipEnable;
        this.desc = desc;
    }

    private ExceptionStep(int step, String desc) {
        this(step, desc, false);
    }

    public boolean isSkipEnable() {
        return skipEnable;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public int getType() {
        return step;
    }

    public int getStep() {
        return step;
    }
    @Override
    public String toString() {
        return new StringBuilder().append(step).append("|").append(skipEnable).append("|").append(desc).toString();
    }
}
