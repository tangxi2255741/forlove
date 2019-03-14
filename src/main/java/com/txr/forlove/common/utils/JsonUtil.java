package com.txr.forlove.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 
 * @author cdyuhuan
 *
 */
public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	public static String toJSONString(Object object)
	{
		try {
			return JSON.toJSONString(object);
		} catch (Exception e) {
			logger.error("转换对象"+object+" json出错:{}",e);
		}
		return null;
		
	}
	
	 public static final <T> T parseObject(String text, Class<T> clazz) {
	      try {
	    	 return JSON.parseObject(text, clazz);
		} catch (Exception e) {
			logger.error("解析字符串:"+text+" json出错:{}",e);
		}
		return null;
	 }

	public static JSONArray parseArray(String jsonStr) {
		 try {
	    	 return JSON.parseArray(jsonStr);
		} catch (Exception e) {
			logger.error("解析字符串:"+jsonStr+" json出错:{}",e);
		}
		return null;
	}

	public static <T> List<T> parseArray(String text, Class<T> clazz)
	{
		try {
			List<T> list = JSON.parseArray(text, clazz);
			return list;
		} catch (Exception e) {
			logger.error("解析字符串:"+text+" json出错:{}",e);
		}
		return null;
	}
	
	public static JSONObject parseObject(String jsonText) {
		 try {
	    	 return JSON.parseObject(jsonText);
		} catch (Exception e) {
			logger.error("解析字符串:"+jsonText+" json出错:{}",e);
		}
		return null;
	}

	public static String toJSONString(Object invoke, boolean b) {
		 try {
	    	 return JSON.toJSONString(invoke,b);
		} catch (Exception e) {
			logger.error("转换对象:"+invoke+"json出错:{}",e);
		}
		return null;
	}

	public static String toJSONString(Object invoke, SerializerFeature... features)
	{
		try {
			return JSON.toJSONString(invoke,features);
		} catch (Exception e) {
			logger.error("转换对象:"+invoke+"json出错:{}",e);
		}
		return null;
	}

	/**
	 * 向json字符串中追加信息,若jsonStr为空或者""则创建新的json字符串
	 * @param jsonStr
	 * @param key
	 * @param value
	 * @return
	 */
	public static String appendToJsonString(String jsonStr, String key, Object value) {
		JSONObject jsonObject;
		if(StringUtils.isEmpty(jsonStr)) {
			jsonObject = new JSONObject();
		} else {
			jsonObject = parseObject(jsonStr);
		}
		if (jsonObject != null) {
			jsonObject.put(key, value);
			return jsonObject.toString();
		}
		return null;
	}

	/**
	 * 从json字符字符串中获取数据
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getValueFromJsonStr(String jsonStr, String key) {
		if (StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
			return null;
		}
		JSONObject jsonObject = parseObject(jsonStr);
		if (jsonObject != null) {
			return jsonObject.get(key);
		}
		return null;
	}
}
