/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.serialization;

import java.io.*;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-14 13:38
 */
public class ObjectStreamSerializer implements Serializer {

    public final static ObjectStreamSerializer INSTANCE = new ObjectStreamSerializer();

    /**
     * 使用java本身的序列化工具，将对象序列化为字节数组
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public <T> byte[] serialize(T obj) throws Exception {
        ByteArrayOutputStream stream = null;
        ObjectOutputStream out = null;
        byte[] bytes = null;
        try {
            stream = new ByteArrayOutputStream();
            out = new ObjectOutputStream(stream);
            out.writeObject(obj);
            out.flush();
            bytes = stream.toByteArray();
        } finally {
            close(out);
            close(stream);
        }
        return bytes;
    }


    /**
     * 使用java本身的序列化工具，将字节数组转为对象
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public  <T> T deserialize(byte[] data, Class<T> cls) throws Exception {
        ByteArrayInputStream stream = null;
        ObjectInputStream in = null;
        Object obj = null;
        try {
            stream = new ByteArrayInputStream(data);
            in = new ObjectInputStream(stream);
            obj = in.readObject();
        } finally {
            close(in);
            close(stream);
        }
        return (T)obj;
    }

    private static void close(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
