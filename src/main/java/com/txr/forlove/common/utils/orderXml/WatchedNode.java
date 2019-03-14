package com.txr.forlove.common.utils.orderXml;

/**
 * xml文档关注节点信息。
 * 
 * @since 2015年6月5日
 * @author yanglei
 *
 */
@SuppressWarnings("rawtypes")
public class WatchedNode {
	private String keyName;
	private Formatter formatter;

	/**
	 * @param keyName 
	 */
	public WatchedNode(String keyName) {
		this(keyName, FormatterFactory.STRING_FORMATTER);
	}

	public WatchedNode(String keyName, Formatter formatter) {
		this.keyName = keyName;
		this.formatter = formatter;
	}

	public String getKeyName() {
		return keyName;
	}

	public Formatter getFormatter() {
		return formatter;
	}

}
