package com.txr.forlove.common.advice.ump;

import com.txr.forlove.common.utils.MessageFormats;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 
 * @since 2016年7月11日
 * @author yanglei
 *
 */
public class OutlineMonitorAdvice extends ProfileMonitor {
	@Override
	protected MonitorType getType() {
		return MonitorType.OUTLINE;
	}

	@Override
	protected String getUmpKey(ProceedingJoinPoint joinPoint) {
		Method method = getMethod(joinPoint);
		return MessageFormats.format(KEY_PATTERN, getType().getType(), method.getDeclaringClass().getName(), method.getName());
	}

	@Override
	protected Method getMethod(ProceedingJoinPoint joinPoint) {
		return ((MethodSignature) joinPoint.getSignature()).getMethod();
	}
}
