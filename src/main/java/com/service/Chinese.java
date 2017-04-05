package com.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liudan19 on 2016/12/22.
 */

@Component
public class Chinese implements ChineseInter {
    public String sayHello(String name){
        System.out.println("--正在执行sayHello方法--");
        return name +"Hello,Spring AOP";
    }
    public void eat(String food){
        System.out.println("我正在吃:"+food);
    }
    public Map<Long, Map<String, String>> queryProductBase(){
        Map<Long, Map<String, String>> map = new HashMap<Long, Map<String, String>>();

        return map;
    }
}
