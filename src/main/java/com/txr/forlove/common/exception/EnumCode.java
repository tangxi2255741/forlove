package com.txr.forlove.common.exception;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举类代码接口
 *
 * @author qiulong
 */
public interface EnumCode<T> {

    /**
     * 获取枚举值
     *
     * @return T
     */
    T getCode();

    /**
     * 获取枚举描述信息
     *
     * @return
     */
    String getDesc();

    class Parser {

        /**
         * 把传递的值转换成指定的枚举类型
         * <p>
         * 注意:会通过{@code equals()}方法对{@linkplain EnumCode#getCode()}返回值与value进行比较
         *
         * @param type  需要转换的枚举类型
         * @param value 传递的值
         * @return 指定的枚举类型
         * @throws IllegalArgumentException 如果没有匹配成功则抛出异常
         */
        @SuppressWarnings("unchecked")
        public static <E extends Enum<E>> E parseTo(Class<E> type, Object value) {
            EnumSet<E> enums = EnumSet.allOf(type);
            for (Enum<E> en : enums) {
                if (!(en instanceof EnumCode)) {
                    throw new IllegalArgumentException(type + " is not implemented " + EnumCode.class);
                }
                EnumCode<?> code = (EnumCode<?>) en;
                if (code.getCode().equals(value)) {
                    return (E) en;
                }
            }
            throw new IllegalArgumentException(String.format("Invalid argument [%s] cannot convert to enum[%s]", value, type));
        }
        

		/**
		 * 
		 * @author yanglei, cdyanglei5@jd.com
		 * @version 1.0.1, 2018年12月28日
		 * @since 2018年12月28日
		 */
        public static <T, E extends EnumCode<T>> Map<T, E> toMap(E[] values) {
    		if (values == null) {
    			throw new IllegalArgumentException("入参不能为空!");
    		}

    		HashMap<T, E> typeMap = new HashMap<T, E>();
    		for (E t : values) {
    			if(typeMap.containsKey(t.getCode())){
    				throw new IllegalArgumentException("具有重复的key[" + t.getCode() + "]值, 不能进行Map转换!");
    			}
    			typeMap.put(t.getCode(), t);
    		}

    		return typeMap;//ImmutableMap.<T, E> copyOf(typeMap);
    	}
    }

    class Comparator {
        public static <E extends Enum<E>> boolean equals(EnumCode<E> e1, EnumCode<E> e2) {
            return e1.getCode().equals(e2);
        }
    }
}
