package com.txr.forlove.common.exception.handlers;

import org.springframework.stereotype.Component;

import com.txr.forlove.common.exception.AppException;
import com.txr.forlove.common.exception.ErrorCode;
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
public class DefaultExceptionHandler extends AbstractExceptionHandler {

    @Override
    protected <E extends Serializable> RPCResult<E> handException(Throwable e, ExceptionContext context) throws AppException {
        context.functionError();
        context.alarm("系统异常:" + e.getMessage());

        String defaultMessage = context.defaultMessage(); // 自定义系统异常信息

        RPCResult<E> rs = RPCResult.failure(ErrorCode.SYSTEM_ERROR.getCode(), defaultMessage != null ? defaultMessage : ErrorCode.SYSTEM_ERROR.getDesc());

        return rs;
    }

    @Override
    public ExceptionStep getStep() {
        return ExceptionStep.throwable;
    }

}
