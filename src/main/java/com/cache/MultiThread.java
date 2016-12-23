package com.cache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.*;
import static java.lang.System.out;
/**
 * Created by liudan19 on 2016/12/23.
 */

public class MultiThread {
    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(90,240,5,TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(500),Executors.defaultThreadFactory());
        for(int i = 0;i<50; i++){
           // CThread t = new CThread();
            CThread   t = ctx.getBean("cThread",CThread .class);
            executor.execute(t);
        }
        executor.shutdown();
    }
}
/**
 * 1.Spring配置的bean 默认为单例 即只会生成一个实例。
 *    故：
 *    在Web程序的Spring上下文中，service是不能有可变状态的。
 *    而本地缓存是可以有可变状态的。
 *    exp:
 *    CacheManager实例中持有一个Map实例称map
 *    在CacheManager.set（）方法中，将数据保存在map    -------->这样CacheManager的状态就发生了改变。
 *
 *    另：
 *    实例状态：即该实例的所有属性组成的状态。
 */















