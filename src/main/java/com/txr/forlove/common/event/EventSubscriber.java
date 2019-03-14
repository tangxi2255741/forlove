package com.txr.forlove.common.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

public interface EventSubscriber {

    /**
     * 处理事件
     *
     * @param event
     */
    @Subscribe
    @AllowConcurrentEvents
    void handle(Event event);
}
