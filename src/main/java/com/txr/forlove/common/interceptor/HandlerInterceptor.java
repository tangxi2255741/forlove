package com.txr.forlove.common.interceptor;

import com.txr.forlove.common.exception.AppException;
import com.txr.forlove.common.handler.Context;
import com.txr.forlove.common.handler.Handler;
import org.springframework.core.Ordered;

/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 */
public interface HandlerInterceptor<T, C extends Context> extends Ordered {
    boolean preHandle(T bo, Handler<T, C> handler) throws AppException;

    void postHandle(T bo, Handler<T, C> handler) throws AppException;

    void afterCompletion(T bo, Handler<T, C> handler) throws AppException;

}
