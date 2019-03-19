/**
 * 
 */
package com.txr.forlove.common.exception.handler;

import com.google.common.collect.Sets;
import com.txr.forlove.common.exception.ErrorCode;
import com.txr.forlove.common.exception.SystemException;
import com.txr.forlove.common.utils.MessageFormats;
import com.txr.forlove.domain.rpc.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
@Service("exceptionAnalyze")
public class ExceptionAnalyzeImpl implements ExceptionAnalyze, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "exceptionHandlerChainFactory")
    protected HandlerChainFactory handlerChainFactory;

    public ExceptionHandlerChain createHandlerChain(ExceptionContext context) {
        Set<ExceptionStep> configStep = Sets.newHashSet(ExceptionStep.values());
        DefaultStepSelector selector = new DefaultStepSelector(configStep, Collections.<ExceptionStep> emptySet());
        
        return handlerChainFactory.create(selector, context);
    }
	@Override
    public <T extends Serializable> RPCResult<T> analyze(Throwable e, ExceptionContext context) {

        try {
            context.failure();
            createHandlerChain(context).doHandler(e);
            return context.getResult();
        } catch (Throwable unexpect) {
            // Not come here!!!
            
            logger.error("{}异常处理出现异常!", context.getModuleDesc(), unexpect);
            
            RPCResult<T> rs = RPCResult.failure(ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getDesc());
//            rs.reqId(B2bLogger.getLogId());
            return rs;
        }
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// jsf不支持在该处回调
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() != null){
			return;
		}
		
		checkProvider(event.getApplicationContext());
	}

	@SuppressWarnings("rawtypes")
	protected void checkProvider(ApplicationContext applicationContext) {
//		Map<String, ? extends ProviderBean> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProviderBean.class);
//
//		for (Map.Entry<String, ? extends ProviderBean> e : beans.entrySet()) {
//			logger.info("Provider[{}]", e.getValue() == null ? "null" : e.getValue().getInterfaceId());
//
//			if(e.getValue() != null){
//				checkProvider(e.getValue().getInterfaceId(), applicationContext);
//			}
//		}
	}
	
	protected void checkProvider(String interfaceId, ApplicationContext applicationContext) {
		try {
			Class<?> clazz = Class.forName(interfaceId);
			for (Method m : clazz.getMethods()) {
				if (!RPCResult.class.isAssignableFrom(m.getReturnType())) {
					logger.error("提供的服务{}#{}返回的类型不是{},请重新定义!!!", interfaceId, m.getName(), RPCResult.class);
					throw new SystemException(MessageFormats.format("提供的服务{}#{}返回的类型不是{},请重新定义!!!", interfaceId, m.getName(), RPCResult.class.getName()));
				}
			}
		} catch (SystemException e) {
			throw e;
		} catch (Exception e) {
			logger.warn("load class[{}] error!", interfaceId);
		}
	}
}
