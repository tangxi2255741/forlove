package com.txr.forlove.common.exception;


/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 */
public class SystemException extends AppException {

    private static final long serialVersionUID = -4977340951719360829L;

    public SystemException(String message) {
        super(ErrorCode.SYSTEM_ERROR, message);
    }

    public SystemException(String message, Throwable cause) {
        super(ErrorCode.SYSTEM_ERROR, message, cause);
    }
}
