package com.txr.forlove.common.utils.orderXml;


import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化工厂类。
 * 
 * @since 2015年6月5日
 * @author yanglei
 */
@ThreadSafe
public class FormatterFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormatterFactory.class);
	/**
	 * 字符串转换。
	 */
	public static final Formatter<String> STRING_FORMATTER = new Formatter<String>() {
		@Override
		public String formate(String value) {
			return value;
		}
	};
	/**
	 * 长整数型转化。
	 */
	public static final Formatter<Long> LONG_FORMATTER = new Formatter<Long>() {
		@Override
		public Long formate(String value) {
			return NumberUtils.toLong(value.trim());
		}
	};
	/**
	 * 整数型转化。
	 */
	public static final Formatter<Integer> INTEGER_FORMATTER = new Formatter<Integer>() {
		@Override
		public Integer formate(String value) {
			return NumberUtils.toInt(value.trim());
		}
	};

    /**
     * 转换为时间类型
     */
    public static final Formatter<Date> DATE_FORMATTER = new Formatter<Date>() {
        @Override
        public Date formate(String value) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return simpleDateFormat.parse(value);
            } catch (java.text.ParseException e) {
                LOGGER.error("订单组订单日期处理出错", e);
            }
            return null;
        }
    };
}
