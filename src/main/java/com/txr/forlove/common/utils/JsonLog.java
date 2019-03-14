package com.txr.forlove.common.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * 
 * @since 2018年3月14日
 * @author yanglei
 *
 */
public class JsonLog {
	protected static final Gson gson;
	private final Object obj;
	static {
		
		//Gson instances are Thread-safe so you can reuse them freely across multiple threads. 
		gson = new GsonBuilder()  
		  .setDateFormat("yyyy-MM-dd HH:mm:ss")
		  .registerTypeHierarchyAdapter(Class.class, new ClassSerializer())
		  .registerTypeHierarchyAdapter(byte[].class, new ByteArraySerializer())
		  .registerTypeHierarchyAdapter(int[].class, new IntArraySerializer())
		  .registerTypeHierarchyAdapter(Throwable.class, new ThrowableSerializer())
		  .serializeNulls()
		  .create(); 
	}
	
	public static JsonLog create(Object obj){
		return new JsonLog(obj);
	}
	
	protected JsonLog(Object obj) {
		this.obj = obj;
	}

	
	@Override
	public String toString() {
		return toJson(obj);
	}
	
	private String toJson(Object src){
		/*Gson gson = new GsonBuilder()  
		  .setDateFormat("yyyy-MM-dd HH:mm:ss")
		  .registerTypeHierarchyAdapter(Class.class, new ClassSerializer())
		  .registerTypeHierarchyAdapter(byte[].class, new ByteArraySerializer())
		  .registerTypeHierarchyAdapter(int[].class, new IntArraySerializer())
		  .registerTypeHierarchyAdapter(Throwable.class, new ThrowableSerializer())
		  .serializeNulls()
		  .create(); */
		try {
			return gson.toJson(src);
		} catch (Exception e) {
			return "[[Data serialize error!";
		}
	}
		
	
	static class ThrowableSerializer implements JsonSerializer<Throwable> {

		@Override
		public JsonElement serialize(Throwable src, Type typeOfSrc, JsonSerializationContext context) {
			StringBuilder builder = new StringBuilder().append("[[Throwable: ");
			if(src != null){
				builder.append(src.getClass().getName()).append("(").append(src.getMessage()).append(")");
			}
			return new JsonPrimitive(builder.toString());
		}

    }
	static class ByteArraySerializer implements JsonSerializer<byte[]> {

		@Override
		public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
			if(src == null || src.length < 100){
				JsonArray array = new JsonArray();
				if(src != null){
					for(int i = 0, size = Math.min(100, src.length); i < size; i++){
						array.add(src[i]);
					}
				}
				return array;
			} else {
				return new JsonPrimitive("[[byte array length: " + src.length);
			}
		}

    }
	
	static class IntArraySerializer implements JsonSerializer<int[]> {

		@Override
		public JsonElement serialize(int[] src, Type typeOfSrc, JsonSerializationContext context) {
			if(src == null || src.length < 100){
				JsonArray array = new JsonArray();
				if(src != null){
					for(int i = 0, size = Math.min(100, src.length); i < size; i++){
						array.add(src[i]);
					}
				}
				return array;
			} else {
				return new JsonPrimitive("[[int array length: "+src.length);
			}
		}

    }
	
	static class ClassSerializer implements JsonSerializer<Class<?>> {

		@Override
		public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive("[[Class: " + src.getName());
		}
    }
}
