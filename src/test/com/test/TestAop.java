package com.test;

import com.service.Chinese;
import com.service.ChineseInter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liudan19 on 2016/12/22.
 */
public class TestAop {
    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        ChineseInter p = ctx.getBean("chinese",ChineseInter.class);
       p.sayHello("tt");
       p.queryProductBase();
    }
}
