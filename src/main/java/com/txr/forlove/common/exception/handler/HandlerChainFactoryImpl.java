package com.txr.forlove.common.exception.handler;

import com.txr.forlove.common.exception.SystemException;
import com.txr.forlove.common.utils.MessageFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
@Service("exceptionHandlerChainFactory")
public class HandlerChainFactoryImpl implements HandlerChainFactory, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<ExceptionStep, ExceptionHandler> handlers;

    @Override
    public ExceptionHandlerChain create(StepSelector selector, ExceptionContext context) {
        return new ExceptionHandlerChain(handlers, selector, context);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Map<String, ExceptionHandler> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ExceptionHandler.class);

        EnumMap<ExceptionStep, ExceptionHandler> handlers = new EnumMap<ExceptionStep, ExceptionHandler>(ExceptionStep.class);

        logger.info("节点[{}]初始化信息>>>", ExceptionHandler.class.getName());

        for (Map.Entry<String, ExceptionHandler> bean : beans.entrySet()) {
            logger.info("[{}]-->{}({})", bean.getValue().getStep(), bean.getKey(), bean.getValue().getClass().getName());

            if (bean.getValue().getStep() == null) {
                throw new SystemException(MessageFormats.format("[{}]支持的节点类型为空!", bean.getValue().getClass().getName()));
            }

            if (handlers.containsKey(bean.getValue().getStep())) {
                throw new SystemException(MessageFormats.format("节点配置重复: [{}]-->[{}] vs [{}]", bean.getValue().getStep(), handlers.get(bean.getValue().getStep())
                        .getClass().getName(), bean.getValue().getClass().getName()));
            }

            handlers.put(bean.getValue().getStep(), bean.getValue());
        }

        logger.info("<<<节点[{}]初始化信息", ExceptionHandler.class.getName());

        this.handlers = Collections.unmodifiableMap(handlers);

    }
}
