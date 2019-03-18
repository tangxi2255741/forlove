package com.txr.forlove.common.handler;

import com.txr.forlove.common.exception.AppException;

/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 * @param <T>
 */
public interface HandlerChain<T> {
    void doHandler(T t) throws AppException;
}
