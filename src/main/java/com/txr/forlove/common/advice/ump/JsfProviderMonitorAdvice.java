package com.txr.forlove.common.advice.ump;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
 * @since 2016年7月11日
 * @author yanglei
 *
 */
public class JsfProviderMonitorAdvice extends ProfileMonitor {

    @Override
    protected MonitorType getType() {
        return MonitorType.JSF_PROVIDER;
    }

    @Override
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return super.doAround(joinPoint);
        } finally {
            logger.info("{}, TotalTimes(ms): {}", getKey(joinPoint), System.currentTimeMillis() - start);
        }
    }

    private String getKey(ProceedingJoinPoint joinPoint) {
        try {
            return getUmpKey(joinPoint);
        } catch (Exception e) {
            return "ErrorKey";
        }
    }

}
