package com.txr.forlove.common.advice.log;

import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月1日
 * @since 2018年8月1日
 * 
 */
public class ConsumerServiceLogAdvice extends LogRecordAdvice {
	public void doBefore(JoinPoint joinPoint) {
		//Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
		Method method = getMethod(joinPoint);
		String[] paramNames = nameDiscoverer.getParameterNames(method);

		Object[] args = joinPoint.getArgs();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		for (int i = 0, size = Math.min(paramNames.length, args.length); i < size; i++) {
			params.put(paramNames[i], args[i]);
		}
		logger.info("RpcConsumer::{}#{}:>>> {}", method.getDeclaringClass().getName(), method.getName(), toJson(params));

	}

	public void doAfter(JoinPoint joinPoint) {

	}

	public void doAfterReturn(JoinPoint joinPoint, Object result) {
		//Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
		Method method = getMethod(joinPoint);
		logger.info("RpcConsumer::{}#{}:<<< {}", method.getDeclaringClass().getName(), method.getName(), toJson(result));
	}

	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		//Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
		Method method = getMethod(joinPoint);
		logger.info("RpcConsumer::{}#{}:<<< ConsumerException!!!", method.getDeclaringClass().getName(), method.getName(), ex);
	}
}
