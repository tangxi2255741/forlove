package com.txr.forlove.common.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月1日
 * @since 2018年8月1日
 * 
 */
public abstract class AdviceSupport {

	protected Method getMethod(JoinPoint joinPoint) {
		return ((MethodSignature) joinPoint.getSignature()).getMethod();
	}
}
