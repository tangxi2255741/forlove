package com.txr.forlove.common.exception.handler;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public interface HandlerChainFactory {

    ExceptionHandlerChain create(StepSelector selector, ExceptionContext context);
}
