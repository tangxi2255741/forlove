package com.txr.forlove.common.advice.log;

import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 方法调用日志切面
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月1日
 * @since 2018年8月1日
 * 
 */
public class MethodCallLogAdvice extends LogRecordAdvice {
	public void doBefore(JoinPoint joinPoint) {
		Method method = getMethod(joinPoint);
		String[] paramNames = nameDiscoverer.getParameterNames(method);

		Object[] args = joinPoint.getArgs();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		for (int i = 0, size = Math.min(paramNames.length, args.length); i < size; i++) {
			params.put(paramNames[i], args[i]);
		}
		logger.info("MethodCall::{}#{}:>>> {}", method.getDeclaringClass().getName(), method.getName(), toJson(params));
		
	}

	public void doAfter(JoinPoint joinPoint) {
	}

	public void doAfterReturn(JoinPoint joinPoint, Object result) {
		Method method = getMethod(joinPoint);
		logger.info("MethodCall::{}#{}:<<< {}", method.getDeclaringClass().getName(), method.getName(), toJson(result));

	}

	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		Method method = getMethod(joinPoint);
		logger.info("MethodCall::{}#{}:<<< MethodCallException!!!", method.getDeclaringClass().getName(), method.getName(), ex);

	}
	@Override
	protected Method getMethod(JoinPoint joinPoint) {
		Method rawMethod = super.getMethod(joinPoint);
		if (!rawMethod.getDeclaringClass().isInterface()) {
			return rawMethod;
		}
		try {
			return joinPoint.getTarget().getClass().getMethod(rawMethod.getName(), rawMethod.getParameterTypes());
		} catch (Exception e) {
			return rawMethod;
		}
	}
}
