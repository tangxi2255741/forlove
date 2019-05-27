package com.txr.forlove.demo.java;

import com.txr.forlove.domain.demo.UserDm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class JavaEightDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaEightDemo.class);

    public static void main(String[] args) {
        List<UserDm> list = UserDm.createdList(20);
        List<String> strings = list.stream().map(UserDm::getName).collect(Collectors.toList());
        LOGGER.info("strings = {}",strings.toString());
    }
}
