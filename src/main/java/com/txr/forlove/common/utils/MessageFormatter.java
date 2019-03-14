package com.txr.forlove.common.utils;


public class MessageFormatter {

    /**
     * 格式化信息<p>
     * 比如:{@code MessageFormatter.format("Hello {}, My name is {}", "World", "Jack")},输出:Hello World, My name is Jack
     *
     * @param messagePattern
     * @param args
     * @return
     */
    final public static String format(String messagePattern, Object... args) {
        if (messagePattern == null || args == null) {
            return messagePattern;
        }
        return  org.slf4j.helpers.MessageFormatter.arrayFormat(messagePattern, args).getMessage();
    }
}
