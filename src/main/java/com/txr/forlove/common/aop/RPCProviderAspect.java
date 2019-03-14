package com.txr.forlove.common.aop;


import com.txr.forlove.common.exception.ErrorCode;
import com.txr.forlove.common.exception.RpcException;
import com.txr.forlove.common.exception.ValidateException;
import com.txr.forlove.common.utils.ClassUtils;
import com.txr.forlove.common.utils.JsonUtil;
import com.txr.forlove.common.utils.UUIDUtils;
import com.txr.forlove.domain.rpc.RPCResult;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 *@Description: 对外提供接口的统一切面
 *@Author: T.X
 *@CreateTime: 2018/9/6 上午10:33
*/
public class RPCProviderAspect extends AbstractAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RPCProviderAspect.class);

    @Override
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        // 记录日志UUID
        UUIDUtils.resetAndGetUUID();
        // 计时器：方法耗时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = null;
        try {
            proceed = jp.proceed();
        }catch (IllegalArgumentException e) {
            LOGGER.error(getClassName(jp) + "#" + getMethodName(jp) + "入参:" + JsonUtil.toJSONString(jp.getArgs()) + "IllegalArgumentException 异常:", e.getMessage());
            proceed = RPCResult.failure(ErrorCode.BIZ_ERROR.getCode(),e.getMessage());
        }catch (ValidateException e) {
            LOGGER.error(getClassName(jp) + "#" + getMethodName(jp) + "入参:" + JsonUtil.toJSONString(jp.getArgs()) + "ValidateException 异常:", e.getMessage());
            proceed = RPCResult.failure(e.getResponseCode().getCode().toString(), e.getMessage());
        }catch (UndeclaredThrowableException e){ //consumer层切面抛出的异常
            Throwable undeclaredThrowable = e.getUndeclaredThrowable();
//            if (undeclaredThrowable instanceof RestrictException || undeclaredThrowable instanceof RpcException) {
//                LOGGER.error(getClassName(jp) + "#" + getMethodName(jp) + "入参:" + JsonUtil.toJSONString(jp.getArgs()) + "RuntimeException|RpcException 异常:", undeclaredThrowable);
//                proceed = RPCResult.failure(ErrorCode.SYSTEM_ERROR.getCode(), "远程调用异常");
//            } else {
//                LOGGER.error(getClassName(jp) + "#" + getMethodName(jp) + "入参:" + JsonUtil.toJSONString(jp.getArgs()) + "UndeclaredThrowableException 异常:", e);
//                proceed = RPCResult.failure(ErrorCode.SYSTEM_ERROR.getCode(), "系统异常");
//            }
        } catch (RpcException e) {
            LOGGER.error(getClassName(jp) + "#" + getMethodName(jp) + "入参:" + JsonUtil.toJSONString(jp.getArgs()) + "调用RpcException 异常:", e);
            proceed = RPCResult.failure(ErrorCode.SYSTEM_ERROR.getCode(), "远程调用异常");
        } catch (Exception e){ //未知异常
            LOGGER.error(getClassName(jp) + "#" + getMethodName(jp) + "入参:" + JsonUtil.toJSONString(jp.getArgs()) + "调用Exception 异常:", e);
            proceed = RPCResult.failure(ErrorCode.SYSTEM_ERROR.getCode(), "系统异常");
        }
        stopWatch.stop();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}#{} ,cost:{}ms 入参:{},返回值:{}", getClassName(jp), getMethodName(jp), stopWatch.getTime(), JsonUtil.toJSONString(jp.getArgs()), JsonUtil.toJSONString(proceed));
        }
        UUIDUtils.clearStart();
        return proceed;
    }

    @Override
    protected Method getMethod(JoinPoint jp) {
        Method method = null;
        Signature signature = jp.getSignature();
        if (MethodSignature.class.isAssignableFrom(signature.getClass())) {
            MethodSignature methodSignature = (MethodSignature) signature;
            method = methodSignature.getMethod();
        } else {
            method = ClassUtils.getMethod(jp.getTarget().getClass(), signature.getName(), this.getParameterType(jp.getArgs()));
        }

        return method;
    }

    private Class<?>[] getParameterType(Object[] args) {
        if (args == null) {
            return null;
        } else {
            int length = args.length;
            Class[] classes = new Class[length];

            for (int i = 0; i < length; ++i) {
                classes[i] = args[i].getClass();
            }

            return classes;
        }
    }
}
