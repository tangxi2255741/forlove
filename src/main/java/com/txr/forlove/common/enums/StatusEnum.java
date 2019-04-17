package com.txr.forlove.common.enums;

import java.util.Map;

/**
 *@Description: 数据有效状态
 *@Author: T.X
 *@CreateTime: 2018/12/29 下午5:54
*/
public enum StatusEnum implements IntegerType,DescType{
    EFFECTIVE(1, "有效"),
    INVALID(-1, "无效"),
    ;

    public static StatusEnum getEnumByCode(int key) {
        for (StatusEnum item : StatusEnum.values()) {
            if (item.getType() == key) {
                return item;
            }
        }
        return null;
    }

    StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final int code;
    private final String desc;

    @Override
    public String getDesc() {
        return desc;
    }
    @Override
    public int getType() {
        return code;
    }

    public static void main(String[] args) {
        Map<Integer, StatusEnum> map =  Enums.toMap(StatusEnum.values());
        System.out.println(map);
    }
}
