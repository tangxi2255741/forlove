package com.txr.forlove.common.event;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cdyuhuan on 2017/4/20.
 */
public class MyThreadFactory implements ThreadFactory {
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;

    MyThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix+"-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread( r,namePrefix + threadNumber.getAndIncrement());
        System.out.println(t.getName());
        if (t.isDaemon())
            t.setDaemon(true);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
