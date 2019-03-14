package com.txr.forlove.common.interceptor;

import com.jd.b2b.restrictsale.cbi.common.handler.Context;
import com.jd.b2b.restrictsale.exception.AppException;
import org.springframework.core.Ordered;

/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 */
public interface Interceptor<T, C extends Context> extends Ordered {
    void preHandle(T bo, C context) throws AppException;

    void postHandle(T bo, C context) throws AppException;

    void afterCompletion(T bo, C context) throws AppException;

}
