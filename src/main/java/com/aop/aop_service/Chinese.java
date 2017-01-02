package com.aop.aop_service;

import org.springframework.stereotype.Component;

/**
 * Created by liudan19 on 2016/12/22.
 */

@Component
public class Chinese {
    public String sayHello(String name,Birthday birthday){
        System.out.println("--正在执行sayHello方法--");
        return name +"Hello,Spring AOP";
    }
    public void eat(String food){
        System.out.println("我正在吃:"+food);
    }
}