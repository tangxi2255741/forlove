package com.txr.forlove.common.utils;

import org.slf4j.helpers.MessageFormatter;

/**
 * 消息格式
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 */
public final class MessageFormats {
	private MessageFormats() {
	}

	public static String concat(Object... args) {
		if (args == null) {
			return null;
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (Object o : args) {
			stringBuilder.append(o);
		}
		return stringBuilder.toString();
	}

	public static String format(String pattern, Object... args) {
		return MessageFormatter.arrayFormat(pattern, args).getMessage();
	}
}
