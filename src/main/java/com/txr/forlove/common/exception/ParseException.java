package com.txr.forlove.common.exception;



/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 */
public class ParseException extends AppException {


    private static final long serialVersionUID = -5599498358575759881L;

    public ParseException(EnumCode<String> code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ParseException(EnumCode<String> code, String message) {
        super(code, message);
    }

    public ParseException(EnumCode<String> code, Throwable cause) {
        super(code, cause);
    }

    public ParseException(EnumCode<String> code) {
        super(code);
    }


}
