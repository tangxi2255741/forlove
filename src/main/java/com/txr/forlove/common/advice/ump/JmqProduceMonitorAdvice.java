package com.txr.forlove.common.advice.ump;

import com.txr.forlove.common.utils.MessageFormats;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * @since 2016年7月11日
 * @author yanglei
 *
 */
public class JmqProduceMonitorAdvice extends ProfileMonitor {

	protected final static String KEY_PATTERN = "{}.MessageProducer.{}";

	@Override
	protected MonitorType getType() {
		return MonitorType.JMQ_PRODUCE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected String getUmpKey(ProceedingJoinPoint joinPoint) {
		String topic = "unKnown";
//		try {
//			Object[] args = joinPoint.getArgs();
//			if (args != null && args.length > 0) {
//				Object arg = args[0];
//				if (arg instanceof Message) {
//					topic = ((Message) arg).getTopic();
//				} else if (arg instanceof List) {
//					List<Object> argList = ((List) arg);
//					if (argList != null && !argList.isEmpty()) {
//						Object a = argList.get(0);
//						if (a instanceof Message) {
//							topic = ((Message) a).getTopic();
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			topic = "ErrorKey";
//		}

		return MessageFormats.format(KEY_PATTERN, getType().getType(), topic);
	}

	@Override
	protected Method getMethod(ProceedingJoinPoint joinPoint) {
		return ((MethodSignature) joinPoint.getSignature()).getMethod();
	}

}
