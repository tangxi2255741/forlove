package com.txr.forlove.common.advice.log;

import com.jd.b2b.restrictsale.cbi.common.exception.BizErrorCode;
import com.jd.b2b.restrictsale.domain.RPCResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月1日
 * @since 2018年8月1日
 * 
 */
public class ProvideServiceLogAdvice extends LogRecordAdvice {
	public void doBefore(JoinPoint joinPoint) {
		//Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
		Method method = getMethod(joinPoint);
		String[] paramNames = nameDiscoverer.getParameterNames(method);
		Object[] args = joinPoint.getArgs();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		for (int i = 0, size = Math.min(paramNames.length, args.length); i < size; i++) {
			params.put(paramNames[i], args[i]);
		}
		logger.info("RpcProvider::{}#{}:>>> {}", method.getDeclaringClass().getName(), method.getName(), toJson(params));

	}

	public void doAfter(JoinPoint joinPoint) {
	}

	public void doAfterReturn(JoinPoint joinPoint, Object result) {
		//Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
		Method method = getMethod(joinPoint);
		logger.info("RpcProvider::{}#{}:<<< {}", method.getDeclaringClass().getName(), method.getName(), toJson(result));
	}

	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		//Logger logger = LoggerFactory.getLogger(joinPoint.getClass());
		Method method = getMethod(joinPoint);
		logger.info("RpcProvider::{}#{}:<<< ProvideException!!!", method.getDeclaringClass().getName(), method.getName(), ex);
	}

	public Object doAround(ProceedingJoinPoint point) throws Throwable{
		String uuid = UUID.randomUUID().toString();
		Object proceed;
		try {
			proceed = point.proceed();
		}catch (SQLException e){
			logger.error("ProvideException->SQLException.uuid={}", uuid, e);
			proceed = RPCResult.failure(BizErrorCode.DB_ACCESS_ERROR.getCode(), BizErrorCode.DB_ACCESS_ERROR.getDesc(), uuid);
		}catch (Exception e){
			logger.error("ProvideException.uuid={}", uuid, e);
			proceed = RPCResult.failure(BizErrorCode.SYSTEM_ERROR.getCode(), BizErrorCode.SYSTEM_ERROR.getDesc(), uuid);
		}

		return proceed;
	}
}
