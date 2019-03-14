package com.txr.forlove.common.event;

import com.jd.b2b.invoice.utils.MessageFormatter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Event {

    private final Type type; // 事件类型
    private String message; // 消息
    private Map<String, Object> params; // 参数

    public interface Type {
        boolean sameAs(Type other);
        String getCode();
    }

    /**
     * 构建一个事件
     *
     * @param type 事件类型
     */
    private Event(Type type) {
        this(type, null);
    }

    /**
     * 构建一个事件
     *
     * @param type 事件类型
     * @param message 消息
     */
    private Event(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * 创建一个不带事件不带消息类型的事件
     *
     * @return
     */
    public static Event newEvent() {
        return newEvent(null);
    }

    /**
     * 创建一个不带事件类型带消息的事件
     *
     * @param message 消息内容
     * @param args 填充消息的参数
     * @return
     */
    public static Event newEvent(String message, Object... args) {
        return newEvent(null, message, args);
    }

    /**
     * 创建一个带事件类型不带消息的事件
     *
     * @param type 消息类型
     * @return
     */
    public static Event newEvent(Type type) {
        return newEvent(type, null, (Object[]) null);
    }

    /**
     * 创建一个带事件类型的事件
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param args 填充消息的参数
     * @return
     */
    public static Event newEvent(Type type, String message, Object... args) {
        return new Event(type, MessageFormatter.format(message, args));
    }

    /**
     * 获取消息
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取事件类型
     *
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     * 设置数据
     *
     * @param key
     * @param value
     * @return
     */
    public Event putParam(String key, Object value) {
        getParams(true).put(key, value);
        return this;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(String key) {
        return (T) getParams(false).get(key);
    }

    private Map<String, Object> getParams(boolean newOne) {
        Map<String, Object> d = params;
        if (d == null) {
            if (newOne) {
                d = new HashMap<String, Object>();
                params = d;
            } else {
                d = Collections.emptyMap();
            }
        }
        return d;
    }
}