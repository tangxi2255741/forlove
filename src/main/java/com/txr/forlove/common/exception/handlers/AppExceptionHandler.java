package com.txr.forlove.common.exception.handlers;

import com.txr.forlove.common.exception.AppException;
import com.txr.forlove.common.exception.handler.AbstractExceptionHandler;
import com.txr.forlove.common.exception.handler.ExceptionContext;
import com.txr.forlove.common.exception.handler.ExceptionStep;
import com.txr.forlove.domain.rpc.RPCResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
@Component
public class AppExceptionHandler extends AbstractExceptionHandler {

    @Override
    protected <E extends Serializable> RPCResult<E> handException(Throwable t, ExceptionContext context) throws AppException {
        AppException e = (AppException) t;

        context.functionError();
        context.alarm(e.getAlarmMessage());

        String defaultMessage = context.defaultMessage();

        RPCResult<E> rs = RPCResult.failure(e.getResponseCode().getCode(), defaultMessage != null ? defaultMessage : e.getMessage());
        return rs;
    }

    @Override
    public boolean supports(Throwable e) {
        return e instanceof AppException;
    }

    @Override
    public ExceptionStep getStep() {
        return ExceptionStep.app_exception;
    }

}
