/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.serialization;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-08-24 16:53
 */
public class ProtoStuffSerializer implements Serializer {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema =  (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    @Override
    public  <T extends Object> byte[] serialize(T obj) throws Exception{
        if(obj == null){
            return null;
        }
        if(obj instanceof byte[]){
            return (byte[])obj;
        }
        Class<T> cls =  (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(cls);

        LinkedBuffer buffer = LinkedBuffer.allocate(256);
        byte[] data  = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        return data;
    }


    @Override
    public  <T> T deserialize(byte[] data,Class<T> cls) throws Exception {
        if(data == null || data.length == 0){
            return null;
        }
        Schema<T> schema = getSchema(cls);
        T message = cls.newInstance();
        ProtostuffIOUtil.mergeFrom(data, message, schema);
        return message;
    }

}
