package com.txr.forlove.common.utils;

import com.jd.b2b.restrictsale.exception.ErrorCode;
import com.jd.b2b.restrictsale.exception.ValidateException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;


/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月3日
 * @since 2018年8月3日
 * 
 */
public abstract class Validates {
	public static void hasText(String text, ErrorCode code, String errorField) {
		if (!StringUtils.hasText(text)) {
			throw new ValidateException(code, errorField);
		}
	}

	public static void hasText(String text, ErrorCode code, String message, String errorField) {
		if (!StringUtils.hasText(text)) {
			throw new ValidateException(code, message, errorField);
		}
	}

	public static void notNull(Object obj, ErrorCode code, String errorField) {
		if (obj == null) {
			throw new ValidateException(code, errorField);
		}
	}

	public static void notNull(Object obj, ErrorCode code, String message, String errorField) {
		if (obj == null) {
			throw new ValidateException(code, message, errorField);
		}
	}

	public static <T> void notEmpty(Collection<T> collection, ErrorCode code, String errorField) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new ValidateException(code, errorField);
		}
	}
	public static <T> void notEmpty(Collection<T> collection, ErrorCode code, String message, String errorField) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new ValidateException(code, message, errorField);
		}
	}
	
	public static <K, V> void notEmpty(Map<K, V> collection, ErrorCode code, String errorField) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new ValidateException(code, errorField);
		}
	}

	public static void isTrue(boolean result, ErrorCode code, String errorField) {
		if (!result) {
			throw new ValidateException(code, errorField);
		}
	}

	public static void isTrue(boolean result, ErrorCode code, String message, String errorField) {
		if (!result) {
			throw new ValidateException(code, message, errorField);
		}
	}

	public static void maxLength(String text, int maxLength, ErrorCode code, String errorField) {
		if (text != null && text.trim().length() > maxLength) {
			throw new ValidateException(code, errorField);
		}
	}
	public static void maxLength(String text, int maxLength, ErrorCode code, String message, String errorField) {
		if (text != null && text.trim().length() > maxLength) {
			throw new ValidateException(code, message, errorField);
		}
	}
	

	public static void size(String text, int min, int max, ErrorCode code, String errorField) {
		int length = 0;
		if (text != null && (length = text.trim().length()) >= min && length <= max) {
			throw new ValidateException(code, errorField);
		}
	}
}
