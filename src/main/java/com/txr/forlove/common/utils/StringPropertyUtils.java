package com.txr.forlove.common.utils;


import org.apache.commons.lang3.StringUtils;

public class StringPropertyUtils {
	/**
	 * 将String aaBc 转为 aa_bc的格式的数据库字段名
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

	/**
	 * @description：aa_bb转换成aaBb
	 * @param：
	 * @return：
	**/
	public static String dbToFiled(String str) {
		if(StringUtils.isBlank(str)){
			return str;
		}
		String[] strArr = str.split("_");
		StringBuilder sb = new StringBuilder();
		sb.append(strArr[0]);
		for(int i=1;i<strArr.length;i++){
			String next = strArr[i];
			String c =  String.valueOf(next.charAt(0));
			next = replaceIndex(next,0,1,c.toUpperCase());
			sb.append(next);
		}
		return sb.toString();
	}

	/**
	 * @description：替换String指定位置的字符
	 * @param：
	 * @return：
	**/
	public static String replaceIndex(String str,int start,int end,String replaceStr){
		StringBuilder sb = new StringBuilder(str);
		return sb.replace(start,end,replaceStr).toString();
	}

	public static void main(String[] args) {
		System.out.println(propertyToFieldName("tangXiTest"));//tang_xi
		System.out.println(dbToFiled("tang_xi_test"));// tangXiTest
	}
}
