package com.txr.forlove.common.interceptor;

import com.txr.forlove.common.exception.AppException;
import com.txr.forlove.common.vein.domain.Context;
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
