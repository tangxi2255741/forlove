package com.txr.forlove.common.advice.ump;

import com.jd.b2b.restrictsale.cbi.common.utils.MessageFormats;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import java.lang.reflect.Method;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月1日
 * @since 2018年8月1日
 * 
 */
public class DataBaseMonitorAdvice extends ProfileMonitor {

	@Override
	protected String getUmpKey(ProceedingJoinPoint joinPoint) {
		Signature s = null;
		if ((s = joinPoint.getSignature()) != null) {
			return MessageFormats.format(KEY_PATTERN, getType().getType(), s.getDeclaringTypeName(), s.getName());
		}

		Method method = getMethod(joinPoint);
		return MessageFormats.format(KEY_PATTERN, getType().getType(), joinPoint.getTarget().getClass().getSimpleName(), method.getName());
	}

	@Override
	protected MonitorType getType() {
		return MonitorType.DATABASE;
	}

}
