package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by liudan19 on 2016/12/23.
 */
public class Monitor {
    public Object paramAround(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        System.out.println("monitor");
        return joinPoint.proceed();
    }
}
