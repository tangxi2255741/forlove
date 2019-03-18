package com.txr.forlove.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CommonCheckUtil
 * @Description: 常用检查工具类
 * @author heguang
 * @date 2014年6月19日 上午10:23:33
 */
public class CommonCheckUtil {
	/** 手机号码规则 */
	//private static final String MOBILE_NUM_REGEX = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$";
	private static final String MOBILE_NUM_REGEX = "^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
	/** 手机号码验证Pattern*/
	private static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_NUM_REGEX);
	
	/**座机号码规则 */
	private static final String PHONE_NUM_REGEX = "[0]{1}[0-9]{2,3}-[0-9]{7,8}"; 
	/**座机验证Pattern */
	private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_NUM_REGEX);

	/** email地址规则 */
	private static final String EMAIL_REGEX = "^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$";
	/** email验证Pattern*/
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public static final boolean checkEmail(String email) {

		if(StringUtils.isBlank(email)){
			return false;
		}

		Matcher m = EMAIL_PATTERN.matcher(email);
		return m.matches();
	}

	public static final boolean checkMobileNmu(String mobileNum) {
		if(StringUtils.isBlank(mobileNum)){
			return false;
		}
		Matcher m = MOBILE_PATTERN.matcher(mobileNum);
		return m.matches();
	}
	
	public static final boolean checkPhoneNum(String phoneNum) {
		if(StringUtils.isBlank(phoneNum)){
			return false;
		}
		Matcher matcher = PHONE_PATTERN.matcher(phoneNum);
		return matcher.matches();
	}

}
