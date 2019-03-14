package com.txr.forlove.common.aop;

import com.alibaba.druid.support.profile.Profiler;
import com.txr.forlove.common.exception.ErrorCode;
import com.txr.forlove.domain.rpc.RPCResult;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author: T.X
 * @create: 2018-12-24 10:35
 **/
public class UmpAspect implements InitializingBean {

    private String appKey= "";

    private boolean enableHeart = true;

    private boolean functionError = true;

    private String systemKey = "";

    private String jvmKey = "";
    private String preFix = "";
    /**
     * 环绕通知：
     * @param jp 当前进程中的连接点
     * @return
     * @throws Throwable
     */
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        Object result = null;
//        CallerInfo info = null;
        try {
            String umpKey = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
            if(StringUtils.isNotBlank(preFix)){
                umpKey = preFix + "." + umpKey;
            }
//            info = Profiler.registerInfo(umpKey, appKey, enableHeart, true);
            result = jp.proceed();
            if(functionError && result != null){
                if (result instanceof RPCResult){
                    RPCResult rs = (RPCResult)result;
                    if (!rs.isSuccess() && (StringUtils.isNotEmpty(rs.getCode()) && rs.getCode().equals(ErrorCode.SYSTEM_ERROR.getCode()))){
//                        Profiler.functionError(info);
                    }
                }
            }
        } catch (Throwable throwable) {
//            if(info != null && functionError) {
//                Profiler.functionError(info);
//            }
            throw throwable;
        }finally {
//            Profiler.registerInfoEnd(info); //方法的可用率
        }
        return result;
    }

    public boolean isEnableHeart() {
        return enableHeart;
    }

    public void setEnableHeart(boolean enableHeart) {
        this.enableHeart = enableHeart;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


    public boolean isFunctionError() {
        return functionError;
    }

    public void setFunctionError(boolean functionError) {
        this.functionError = functionError;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getJvmKey() {
        return jvmKey;
    }

    public void setJvmKey(String jvmKey) {
        this.jvmKey = jvmKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.systemKey != null && !"".equals(this.systemKey.trim())) {
//            Profiler.InitHeartBeats(this.systemKey);
        }

        if(this.jvmKey != null && !"".equals(this.jvmKey.trim())) {
//            Profiler.registerJVMInfo(this.jvmKey);
        }
    }

    public String getPreFix() {
        return preFix;
    }

    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }
}
