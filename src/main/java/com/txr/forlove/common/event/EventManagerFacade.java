package com.txr.forlove.common.event;


import com.txr.forlove.common.exception.EnumCode;

/**
 * Created by cdyuhuan on 2016/11/21.
 */
public class EventManagerFacade {

    private static final EventManager eventManager = new EventBusManager();

    /**
     * 发布一个事件
     *
     * @param topic 事件主题
     * @param event 事件
     */
    public static void pubEvent(EnumCode<String> topic, Event event) {
        eventManager.pub(topic.getCode(), event);
    }

    /**
     * 订阅一个事件
     *
     * @param topic 事件主题
     * @param subscriber 事件订阅者
     */
    public static void subEvent(EnumCode<String> topic, EventSubscriber subscriber) {
        eventManager.sub(topic.getCode(), subscriber);
    }

    /**
     * 取消订阅
     *
     * @param topic
     * @param subscriber
     */
    public static void unsubEvent(EnumCode<String> topic, EventSubscriber subscriber) {
        eventManager.unsub(topic.getCode(), subscriber);
    }
}
