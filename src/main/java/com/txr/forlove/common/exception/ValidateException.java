package com.txr.forlove.common.exception;

/**
 * @author: T.X
 * @create: 2018-12-20 14:08
 **/
public class ValidateException extends AppException {

    private static final long serialVersionUID = 961794978717991823L;

    private final String errorField;

    public ValidateException(EnumCode<String> code, String message, Throwable cause, String errorField) {
        super(code, message, cause);
        this.errorField = errorField;
    }

    public ValidateException(EnumCode<String> code, String message, String errorField) {
        super(code, message);
        this.errorField = errorField;
    }

    public ValidateException(EnumCode<String> code, Throwable cause, String errorField) {
        super(code, cause);
        this.errorField = errorField;
    }

    public ValidateException(EnumCode<String> code, String errorField) {
        super(code);
        this.errorField = errorField;
    }

    public String getErrorField() {
        return errorField;
    }
}
