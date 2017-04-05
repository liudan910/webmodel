package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by liudan19 on 2016/12/23.
 */
public class Monitor {
    public Object paramAround(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println(method.getGenericReturnType());
        return joinPoint.proceed();
    }
}
