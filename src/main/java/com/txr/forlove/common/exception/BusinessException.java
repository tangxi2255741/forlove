package com.txr.forlove.common.exception;



/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 */
public class BusinessException extends AppException {

    private static final long serialVersionUID = 7389967345584257398L;

    public BusinessException(EnumCode<String> code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BusinessException(EnumCode<String> code, String message) {
        super(code, message);
    }

    public BusinessException(EnumCode<String> code, Throwable cause) {
        super(code, cause);
    }

    public BusinessException(EnumCode<String> code) {
        super(code);
    }


}
