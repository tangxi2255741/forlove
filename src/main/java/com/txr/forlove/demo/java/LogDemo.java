package com.txr.forlove.demo.java;

import com.txr.forlove.domain.demo.UserDm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LogDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogDemo.class);

    public static void main(String[] args) {
        //耗时打印
        long start = System.currentTimeMillis();
        List<UserDm> list = UserDm.createdList(20);
        LOGGER.info("{}条数据放入缓存耗时：{}ms",list.size(),(System.currentTimeMillis() - start));
    }
}
