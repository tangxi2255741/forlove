package com.txr.forlove.common.interceptor;

import com.jd.b2b.restrictsale.cbi.common.handler.Context;
import com.jd.b2b.restrictsale.cbi.common.handler.Handler;
import com.jd.b2b.restrictsale.exception.AppException;
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
