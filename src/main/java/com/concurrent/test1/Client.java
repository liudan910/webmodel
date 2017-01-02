package com.concurrent.test1;


import com.util.RandonUTil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;
/**
 * 测试一
 场景：
 1.线程池执行器EventExecutor用于并发 请求上游数据
 2.service用于处理业务，自动注入EventExecutor
 3.模拟web服务器中，每个请求一个线程，由service处理
 4.EventExecutor 和 service均配置为bean,即单例模式
 测试：
 线程池执行器中的 任务 是 单个请求的，还是所有请求的？
 代码
 Client 模拟 客户端， 不断发送请求
 Server模拟服务端，不断接收请求，每个请求都会调用 DynamicService来处理，
    DynamicService将 利用EventExecutor并发请求多个上游接口
 */
class Request{
    public String name;
    public String interfaceName;
    public Request(String name,String interfaceName){
        this.name = name;
        this.interfaceName = interfaceName;
    }
}
class RequestThread implements Runnable{
    public boolean runnable = true;
    public String interfaceName;
    Server server;
    CountDownLatch latch;
    public RequestThread(String interfaceName,Server server,CountDownLatch latch){
        this.interfaceName = interfaceName;
        this.server = server;
        this.latch = latch;
    }
    public void run() {
        //while(runnable){
            try {
                latch.await();
                Thread.sleep(randonTime());
                server.dealQuest(new Request(RandonUTil.getRandomKey(),interfaceName));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        //}
    }
    static int randonTime(){
        return (int)Math.random()*10;
    }
}
public class Client {
    RequestThread [] threads;
    Server server;
    CountDownLatch latch;
    public Client(Server server,CountDownLatch latch){
        this.server = server;
        this.latch = latch;
    }
    public void webserver() throws InterruptedException {
        //服务器只有一个
        threads = new RequestThread [10];
        String[] iNames = {"static","dynamic","picture"};
        for(int i = 0;i<10;i++){        //10个请求
            String interfaceName = iNames[i%3];
            threads[i] = new RequestThread (interfaceName,server,latch); //向服务器发送请求
            Thread thread = new Thread(threads[i]);
            thread.start();
        }
    }
    public void shutdown(){
        for(int i = 0;i<10;i++){
            threads[i].runnable = false;
            threads[i] = null;
        }
        threads = null;
    }
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        final Server server = ctx.getBean("server",Server.class);
        CountDownLatch latch = new CountDownLatch(1);
        Client client =  new Client(server,latch);
        client .webserver();     //启动服务
        LookThread lookThread = new LookThread(server,latch);
        Thread thread = new Thread(lookThread);
        thread.start();
        latch.countDown();                      //使服务器 及 监视线程池的线程同时开始
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        while(true) {
            if (end - start < 3000) {
                end = System.currentTimeMillis();
            }else{
                break;
            }
        }
        lookThread.Runnable = false;
    }
}

/**
 * 监视线程池的线程
 */
class LookThread implements Runnable{
    public boolean Runnable= true;
    Server server;
    CountDownLatch latch;
    public LookThread(Server server,CountDownLatch latch){
        this.server = server;
        this.latch = latch;
    }
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("live的线程数(动态的）");
        int activeCount = 0;
        while(Runnable) {
            activeCount = server.dynamicService.eventExecutor.getActiveCount();
            if(activeCount>0){
                out.println(activeCount);
            }
        }
        out.println("完成任务数:");
        out.println(server.dynamicService.eventExecutor.getCompletedTaskCount());
    }
}
/**
 测试结果 ： 线程池中：
    运行线程数： 0-1
    完成任务数:50

 使用同一个执行器，提交了50个任务，但只用了一个线程。
结：
 由线程池执行器是单例的，所有请求线程都使用同一个执行器。对于执行器数来说：
 线程池执行器的 任务数 = 请求数 * 请求的上游接口
故对SOA来说：
   线程核心数： 90，最大240， 任务队列长度：500
 对执行器来说，任务数 = SOA提交的任务数。
               线程实际数量 由 执行器内部管理。


 */