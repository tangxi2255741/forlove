package com.txr.forlove.common.handler;

import com.jd.b2b.restrictsale.exception.AppException;
import org.springframework.core.Ordered;

/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 * @param <T>
 */
public interface Handler<T, C extends Context> extends Ordered {
    void handle(T t, C context, HandlerChain<T> chain) throws AppException;
}
