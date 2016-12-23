package com.test;

import com.service.Birthday;
import com.service.Chinese;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liudan19 on 2016/12/22.
 */
public class TestAop {
    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        Chinese p = ctx.getBean("chinese",Chinese.class);
        Birthday birthday = new Birthday(1992,12,5);
        p.sayHello("李三",birthday);
    }
}
