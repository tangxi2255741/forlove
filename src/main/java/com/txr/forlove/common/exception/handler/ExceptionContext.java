package com.txr.forlove.common.exception.handler;

import com.txr.forlove.common.constants.UmpConstants;
import com.txr.forlove.common.exception.monitor.Monitor;
import com.txr.forlove.common.handler.Context;
import com.txr.forlove.domain.rpc.RPCResult;

import java.io.Serializable;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public class ExceptionContext implements Context {
    private static final long serialVersionUID = -7864795268612158489L;


    private final String moduleDesc;
    private final String alarmKey;
    private final Monitor monitor;
    
    public ExceptionContext(String moduleDesc, String alarmKey, Monitor monitor) {
        super();
        this.moduleDesc = moduleDesc;
        this.alarmKey = UmpConstants.JSF_PROVIDER_ALARM_ROOT + alarmKey;
        this.monitor = monitor;
    }

    @SuppressWarnings("rawtypes")
    private RPCResult result;

    public String getModuleDesc() { 
        return moduleDesc;
    }

    public String defaultMessage() {
        return null;
    }

    public void functionError() {
        monitor.functionError();
    }

    public void alarm(String message) {
//        UmpAlarm.alarm(alarmKey, getModuleDesc() + message);
    }

    public void failure() {
        monitor.failure();
    }

    public void errorCode(String errorCode) {
        monitor.errorCode(errorCode);
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> RPCResult<T> getResult() {
        return result;
    }

    public <T extends Serializable> void setResult(RPCResult<T> result) {

        this.result = result;
    }

}
