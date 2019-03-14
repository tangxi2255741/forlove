package com.txr.forlove.common.exception;


import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @since 2017年10月16日
 * @author yanglei
 *
 */
public abstract class RpcException extends AppException {

    private static final long serialVersionUID = 2312995172812267587L;
    protected Class<?> interfaceName;
    protected String method;
    
    public RpcException(EnumCode<String> code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public RpcException(EnumCode<String> code, String message) {
        super(code, message);
    }

    public RpcException(EnumCode<String> code, Throwable cause) {
        super(code, cause);
    }

    public RpcException(EnumCode<String> code) {
        super(code);
    }
    
    public abstract String getServiceName();
    public abstract String getMethod();
    
    public abstract boolean isTimeout();

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if (StringUtils.isBlank(msg)) {
            if (enumCode != null) {
                msg = enumCode.getDesc();
            } else {
                msg = ErrorCode.RPC_ERROR.getDesc() + "[RPC]";
            }
        }

        return msg;
    }

}
