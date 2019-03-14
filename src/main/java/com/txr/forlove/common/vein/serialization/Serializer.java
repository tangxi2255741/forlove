/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.serialization;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-14 13:33
 */
public interface Serializer {

    /***
     * 将对象转成byte[]
     * @param obj
     * @param <T>
     * @return
     */
    <T extends Object> byte[] serialize(T obj) throws Exception;

    /***
     * 将byte[]转成对象
     * @param data
     * @param cls
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> cls) throws Exception;
}
