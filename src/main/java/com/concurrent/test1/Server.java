package com.concurrent.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import static java.lang.System.out;
/**
 * Created by liuda on 2017/1/2.
 */
@Component
class Handler extends Thread{
    @Override
    public void run(){
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        int interval = randonTime();
        while(true) {
            if (end - start < interval) {
                end = System.currentTimeMillis();
            }else{
                break;
            }
        }
    }
    static int randonTime(){
        return (int)Math.random()*20;
    }
}

@Component
class DynamicService{
    @Autowired
    public EventExecutor eventExecutor;
    public void dealQuest(Request request){         //请求5个上游接口
        for(int i = 0;i<5;i++){
            eventExecutor.execute(new Handler());
        }
    }

}

@Component
public class Server {
    @Autowired
    public DynamicService dynamicService;
    public void dealQuest(Request request){
        dynamicService.dealQuest(request);
    }
}
/**
 10个请求， 5个上游接口
 */