package com.txr.forlove.common.event;


public interface EventManager {
    /**
     * 发布一个事件
     *
     * @param topic 事件主题
     * @param event 事件
     */
    void pub(String topic, Event event);

    /**
     * 订阅一个事件
     *
     * @param topic 事件主题
     * @param subscriber 事件订阅者
     */
    void sub(String topic, EventSubscriber subscriber);

    /**
     * 取消订阅
     *
     * @param topic
     * @param subscriber
     */
    void unsub(String topic, EventSubscriber subscriber);
}
