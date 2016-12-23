package com.aop;

import com.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by liudan19 on 2016/12/23.
 */
public class Monitor2 {
    public Object paramAround(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("monitor2----------");
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Object[] args = joinPoint.getArgs();
        for(Object arg:args){
            System.out.println(JsonUtil.write2JsonStr(arg));
        }
        System.out.println("monitor2----------");
        return joinPoint.proceed();
    }
}
