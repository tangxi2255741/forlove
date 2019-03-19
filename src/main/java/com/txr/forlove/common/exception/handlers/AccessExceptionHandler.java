package com.txr.forlove.common.exception.handlers;

import com.txr.forlove.common.exception.AccessException;
import com.txr.forlove.common.exception.AppException;
import com.txr.forlove.common.exception.ErrorCode;
import com.txr.forlove.common.exception.handler.AbstractExceptionHandler;
import com.txr.forlove.common.exception.handler.ExceptionContext;
import com.txr.forlove.common.exception.handler.ExceptionStep;
import com.txr.forlove.domain.rpc.RPCResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 
 * @since 2017年11月17日
 * @author yanglei
 *
 */
@Component
public class AccessExceptionHandler extends AbstractExceptionHandler {

	@Override
	protected <E extends Serializable> RPCResult<E> handException(Throwable t, ExceptionContext context) throws AppException {
		AccessException e = (AccessException) t;

		context.functionError();
		context.alarm(e.getAlarmMessage());
		String resultMsg = e.getMessage();

		return RPCResult.failure(null != e.getResponseCode() ? e.getResponseCode().getCode() : ErrorCode.SYSTEM_ERROR.getCode(), resultMsg);
	}

	@Override
	public boolean supports(Throwable e) {
		return e instanceof AccessException;
	}

	@Override
	public ExceptionStep getStep() {
		return ExceptionStep.access_exception;
	}

}
