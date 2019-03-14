package com.txr.forlove.common.event;

import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


class EventBusManager implements EventManager {

    private ConcurrentHashMap<String, AsyncEventBus> manager = new ConcurrentHashMap<String, AsyncEventBus>();

    private Executor executor =  Executors.newFixedThreadPool(10,new MyThreadFactory("Guava-thread"));

    @Override
    public void pub(String topic, Event event) {
        AsyncEventBus eventBus = manager.get(topic);
        if (eventBus == null) {
            eventBus = new AsyncEventBus(topic,executor);
            AsyncEventBus old = manager.putIfAbsent(topic, eventBus);
            if (old != null) {
                eventBus = old;
            }
        }
        eventBus.post(event);
    }

    @Override
    public void sub(String topic, EventSubscriber subscriber) {
        AsyncEventBus eventBus = manager.get(topic);
        if (eventBus == null) {
            eventBus = new AsyncEventBus(topic,executor);
            AsyncEventBus old = manager.putIfAbsent(topic, eventBus);
            if (old != null) {
                eventBus = old;
            }
        }
        eventBus.register(subscriber);
    }

    @Override
    public void unsub(String topic, EventSubscriber subscriber) {
        AsyncEventBus eventBus = manager.get(topic);
        if (eventBus != null) {
            eventBus.unregister(subscriber);
        }
    }

}
