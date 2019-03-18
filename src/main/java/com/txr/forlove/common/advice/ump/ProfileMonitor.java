package com.txr.forlove.common.advice.ump;

import com.txr.forlove.common.advice.AdviceSupport;
import com.txr.forlove.common.utils.MessageFormats;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import java.lang.reflect.Method;

/**
 * 
 * @since 2016年7月12日
 * @author yanglei
 *
 */
public abstract class ProfileMonitor extends AdviceSupport {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final static String KEY_PATTERN = "{}.{}.{}";

	protected String getUmpKey(ProceedingJoinPoint joinPoint) {
		Method method = getMethod(joinPoint);
		return MessageFormats.format(KEY_PATTERN, getType().getType(), method.getDeclaringClass().getSimpleName(), method.getName());
	}

	protected Method getMethod(ProceedingJoinPoint joinPoint) {
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

	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		CallerInfo callerInfo = null;
		try {
			String monitorKey = getUmpKey(joinPoint);
			if(logger.isDebugEnabled()){
				logger.debug("监控key : {}", monitorKey);
			}
//			callerInfo = UmpProfiler.registerInfo(monitorKey, false, true);
		} catch (Exception e) {
			logger.error("获取Ump监控Key异常!-joinPoint: {}", joinPoint, e);
		} 
		Object resultVal;
		try {
			resultVal = joinPoint.proceed();
		}catch (DuplicateKeyException dke){// 主键冲突异常不降低可用率
			throw dke;
		}catch (Exception e) {
//			if (callerInfo != null) {
//				Profiler.functionError(callerInfo);
//			}
			throw e;
		} finally {
//			if (callerInfo != null) {
//				Profiler.registerInfoEnd(callerInfo);
//			}
		}
		return resultVal;
	}

	protected abstract MonitorType getType();
	

}
