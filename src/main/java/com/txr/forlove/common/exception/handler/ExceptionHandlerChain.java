package com.txr.forlove.common.exception.handler;


import com.txr.forlove.common.handler.AbstractHandlerChain;

import java.util.Map;


/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public class ExceptionHandlerChain extends AbstractHandlerChain<Throwable, ExceptionContext, ExceptionHandler> {

    private final StepSelector stepSelector;
    private final Map<ExceptionStep, ExceptionHandler> handlers;

    public ExceptionHandlerChain(Map<ExceptionStep, ExceptionHandler> handlers, StepSelector stepSelector, ExceptionContext context) {
        super(context);
        this.stepSelector = stepSelector;
        this.handlers = handlers;
    }

    @Override
    protected ExceptionHandler getNext(Throwable t) {
        ExceptionStep step = stepSelector.next();
        if (step == null) {
            // 没有后续步骤
            return null;
        }

        ExceptionHandler handler = handlers.get(step);

        if (!handler.supports(t)) {
            logger.info("节点[{}]->[{}]不支持当前业务!", step, handler.getClass().getName());
            return getNext(t);
        }

        return handler;
    }

}
