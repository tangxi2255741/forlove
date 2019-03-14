package com.txr.forlove.common.aop;

/**
 * @author: T.X
 * @create: 2018-12-20 13:47
 **/

import com.txr.forlove.common.utils.ClassUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public abstract class AbstractAspect {
    public AbstractAspect() {
    }

    public void before(JoinPoint jp) throws Throwable {
    }

    public void afterReturning(JoinPoint jp, Object retVal) {
    }

    public void afterThrowing(JoinPoint jp, Throwable e) throws Throwable {
        throw e;
    }

    public void after(JoinPoint jp) {
    }

    public Object around(ProceedingJoinPoint jp) throws Throwable {
        return jp.proceed(jp.getArgs());
    }

    protected String getClassName(JoinPoint jp) {
        return jp.getTarget().getClass().getCanonicalName();
    }

    protected String getMethodName(JoinPoint jp) {
        return this.getMethod(jp).getName();
    }

    protected Method getMethod(JoinPoint jp) {
        Signature signature = jp.getSignature();
        Method method;
        if (MethodSignature.class.isAssignableFrom(signature.getClass())) {
            MethodSignature methodSignature = (MethodSignature)signature;
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
            Class<?>[] classes = new Class[length];

            for(int i = 0; i < length; ++i) {
                classes[i] = args[i].getClass();
            }

            return classes;
        }
    }
}