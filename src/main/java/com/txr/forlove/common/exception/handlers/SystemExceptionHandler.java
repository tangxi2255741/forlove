package com.txr.forlove.common.exception.handlers;

import com.txr.forlove.common.exception.*;
import org.springframework.stereotype.Component;
import com.txr.forlove.common.exception.handler.AbstractExceptionHandler;
import com.txr.forlove.common.exception.handler.ExceptionContext;
import com.txr.forlove.common.exception.handler.ExceptionStep;
import com.txr.forlove.domain.rpc.RPCResult;
import java.io.Serializable;
/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
@Component
public class SystemExceptionHandler extends AbstractExceptionHandler {

    @Override
    protected <E extends Serializable> RPCResult<E> handException(Throwable t, ExceptionContext context) throws AppException {
        SystemException e = (SystemException) t;

        context.functionError();
        context.alarm(e.getAlarmMessage());

        RPCResult<E> rs = RPCResult.failure(e.getResponseCode().getCode(), e.getMessage());
        return rs;
    }

   

    @Override
    public boolean supports(Throwable e) {
        return e instanceof SystemException;
    }

    @Override
    public ExceptionStep getStep() {
        return ExceptionStep.system_exception;
    }

}
