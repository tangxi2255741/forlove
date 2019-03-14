package com.txr.forlove.common.utils;

import org.apache.commons.lang.StringUtils;

public class StringPropertyUtils {
	/**
	 * 将String aaBc 转为 aa_bc的格式的数据库字段名
	 * 
	 * @param str
	 * @return
	 */
	public static String propertyToFieldName(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		if (str.charAt(0) > 'A' && str.charAt(0) < 'Z') {
			return str;
		}
		if (Character.isUpperCase(str.charAt(0))) {
			str = Character.toString(Character.toLowerCase(str.charAt(0))) + str.substring(1);
		}
		for (int i = 1; i < str.length(); i++) {
			char a = str.charAt(i);
			if (a >= 'A' && a <= 'Z') {
				if (str.charAt(i - 1) != '_') {
					str = str.replace(a + "", ("_" + a).toLowerCase());
					i++;
				}
				continue;
			}
		}
		return str;
	}
}
