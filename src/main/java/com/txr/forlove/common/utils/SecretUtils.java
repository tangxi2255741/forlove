package com.txr.forlove.common.utils;

import java.util.UUID;

/**
 * 密钥生成工具类
 */
public class SecretUtils {
	
	/**
	 * 生成KEY
	 * 规则:系统时间戳字符串+系统UUID去“-”后的字符串 最后MD5
	 * @return
	 */
	public static String createAccessKey(){
		StringBuffer sf = new StringBuffer();
		sf.append(System.currentTimeMillis());
		sf.append(UUIDUtils.getSimpleUuid());
		String str = MD5Utils.useMD5(sf.toString());
		if(null != str){
			return str;
		}
		return null;
	}
	
	
	/**
	 * 生成secret
	 * 规则:系统UUID去“-”后的字符串+系统时间戳字符串 倒序排列  最后MD5
	 * @return
	 */
	public static String createSecretKey(){
		StringBuffer sf = new StringBuffer();
		sf.append(UUID.randomUUID().toString().replaceAll("-", ""));
		sf.append(System.currentTimeMillis());
		sf.reverse();//倒序排列
		String str = MD5Utils.useMD5(sf.toString());
		if(null!=str){
			return str;
		}
		return null;
	}
	
	//生成随机串－数字
	public static String generateNumberCode(int num) {
		 String[] nums = {"0","1","2","3","4","5","6","7","8","9"};
	     String res = "";
	     for(int i = 0; i < num ; i ++) {
	         int id = (int) Math.ceil(Math.random()*9);
	         res += nums[id];
	     }
	     return res;
	}
	
	//生成随机串－字符
	public static String generateStringCode(int num) {
		 String[] chars ={"a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z"};
	     String res = "";
	     for(int i = 0; i < num ; i ++) {
	         int id = (int) Math.ceil(Math.random()*23);
	         res += chars[id];
	     }
	     return res;
	}
	
	//生成随机串－混合
	public static String generateCode(int num) {
		 String[] chars ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z"};
	     String res = "";
	     for(int i = 0; i < num ; i ++) {
	         int id = (int) Math.ceil(Math.random()*33);
	         res += chars[id];
	     }
	     return res;
	}

	public static void main(String[] args) {
		System.out.println(SecretUtils.createAccessKey());// df72b2897945fc05ff91b11f4c1dc6a2
		System.out.println(SecretUtils.createSecretKey());// 5528d42040d97030bec9084b22dbabd8
		System.out.println(generateCode(2));// 9v
		System.out.println(generateNumberCode(3));// 594
		System.out.println(generateStringCode(4));// uvvk
	}

}
