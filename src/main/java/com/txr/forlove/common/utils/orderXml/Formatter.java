package com.txr.forlove.common.utils.orderXml;

/**
 * 数据格式化，将输入参数转化为指定数据类型。
 * 实现类必须是线程安全.
 * 
 * @since 2015年6月5日
 * @author yanglei
 *
 */
public interface Formatter<T> {
	/**
	 * 数据类型转换。
	 * 
	 * @param value 输入参数
	 * @return 转换结果值
	 */
	T formate(String value);
}
