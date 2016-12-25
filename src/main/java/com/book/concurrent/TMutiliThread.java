package com.book.concurrent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by czc on 2016/12/25.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

/**
 * Created by liudan19 on 2016/12/23.
 */

class CThread extends Thread{
    TCacheManage cacheManager = new TCacheManage();

    //CacheTest test = new CacheTest();
    @Override
    public void run(){
        String key = getRandomKey();
        String value = "1";
        cacheManager.set(key,value);
        //test.set(key,value);
    }
    public  String getRandomKey(){
        String sample = "ABCDEFGHIJKLMNOPQRSTUCWXYZ";
        int x = 0;
        while((x=getRandom())<2){
        }
        return sample.substring(x-2,x);
    }
    public int getRandom(){
        return (int)(Math.random()*26);
    }
}

public class TMutiliThread {

    /**
     * Created by liudan19 on 2016/12/23.
     */

        public static void main(String[] args){

            ThreadPoolExecutor executor = new ThreadPoolExecutor(90,240,5, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(500), Executors.defaultThreadFactory());
           CThread t = new CThread();
            for(int i = 0;i<50; i++){
                executor.execute(t);
            }

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















