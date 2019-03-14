package com.txr.forlove.common.utils.orderXml;

/**
 * 解析异常类
 * 
 * @since 2015年6月5日
 * @author yanglei
 *
 */
public class ParseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7881371604101530124L;

	public ParseException() {
		super();
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

}
