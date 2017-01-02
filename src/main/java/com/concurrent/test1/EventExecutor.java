package com.concurrent.test1;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuda on 2017/1/2.
 */
public //@Component("eventExecutor") 因为没有无参的默认构造器，无法使用@Component,需要在配置文件 中配置，将设置构造函数参数
class EventExecutor extends ThreadPoolExecutor {
    public EventExecutor(int corePoolSize, int maximumPoolSize,long keepAliveTime,int blockingqueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(blockingqueue), Executors.defaultThreadFactory());
    }
    public void printInfo(){

    }
}