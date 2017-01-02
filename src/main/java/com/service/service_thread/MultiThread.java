package com.service.service_thread;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by liudan19 on 2016/12/23.
 */
class Caculator {
    Integer c = 0;

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }
}

class PTask implements Runnable {
    Caculator caculator;
    public PTask(Caculator caculator){
        this.caculator = caculator;
    }
    public void run() {
        int x = caculator.getC();
        x++;
        caculator.setC(x);
        out.println("p: " + x);
    }
}

class CTask implements Runnable {
    Caculator caculator;
    public CTask(Caculator caculator){
        this.caculator = caculator;
    }
    public void run() {
        int x = caculator.getC();
        out.println("c:"+x);
    }
}

public class MultiThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }

    public static void test1() throws ExecutionException, InterruptedException {
        final CountDownLatch endLatch = new CountDownLatch(200);
        final CountDownLatch startLatch = new CountDownLatch(1);
        Caculator caculator = new Caculator();
        final Thread pThread = new Thread(new PTask(caculator));
        final Thread cThread = new Thread(new CTask(caculator)); //编译提示的，因为被内部类访问，故必须设置为final
        for (int i = 0; i < 50; i++) {
            Thread thread1 = new Thread(new Runnable() {
                public void run() {
                    try {
                        startLatch.await();
                        pThread.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        endLatch.countDown();
                    }
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                public void run() {
                    try {
                        startLatch.await();
                        cThread.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        endLatch.countDown();
                    }
                }
            });
            thread1.start();
            thread2.start();
        }
        //主线程
        long start = System.currentTimeMillis();
        startLatch.countDown();          //所有线程开始启动
        endLatch.await();               //等待所有线程结束
        long end = System.currentTimeMillis();
        out.println(end - start);
    }
}

/**
 * 1.countDownLatch
 * 利用countDownLatch作启动门，以确保所有线程就绪后开始。（若是新建完就开始，会导致先建的线程先执行）
 * 利用countDownLatch作结束门，使主线程得知所有线程均完成。
 * 应用： 统计所消耗的时间； 使线程开始时间更加统一
 * <p/>
 * 2
 * 测试一： 基本数据类型计数器与原子类实现的计数器对比
 * c++ 操作必须作为一个原子操作,且由持有c的对象实现原子封装。
 * exp:
 * 线程1 读-改-写：
 * c = objectA .get();
 * c++;
 * objectA.set();
 * 线程2 读
 * 在线程1内 进行同步是没有用的，尽管objectA.get(),set()方法是synchronized的，但在读-改-写时不是原子性的。
 * <p/>
 * 同步方法： 将c++操作封装在同步方法objectA.add()，由线程1调用。
 * exp:
 * ObjectA{
 * int c;
 * synchronized add(){ c++; }
 * }
 * 线程1：
 * objectA.add();
 * <p/>
 * 1.executor.shutdownNow()操作
 * 线程不会因此停止，该操作只是将线程的中断标志位置1.
 * 线程停止只能由线程本身决定，两种方式：
 * 任务运行完成 、自己提前结束任务。
 * 2.Thread.start()不能重复调用,故以下代码会出错
 * 3.CountDownLatch.await() 与 CountDownLatch.wait()区别：
 * CountDownLatch.wait() 调用的是Class对象的wait()
 * CountDownLatch.await()为CountDownLatch类需要实现的方法：即等待计数器变为0
 */

/**
 1.executor.shutdownNow()操作
 线程不会因此停止，该操作只是将线程的中断标志位置1.
 线程停止只能由线程本身决定，两种方式：
 任务运行完成 、自己提前结束任务。
 2.Thread.start()不能重复调用,故以下代码会出错
 3.CountDownLatch.await() 与 CountDownLatch.wait()区别：
 CountDownLatch.wait() 调用的是Class对象的wait()
 CountDownLatch.await()为CountDownLatch类需要实现的方法：即等待计数器变为0
 */









