package com.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by czc on 2016/12/25.
 */
public class ThreadPool {
    public static void main(String[] args){
        /**
         *     public ThreadPoolExecutor(int corePoolSize,
         int maximumPoolSize,
         long keepAliveTime,
         TimeUnit unit,
         BlockingQueue<Runnable> workQueue,
         ThreadFactory threadFactory,
         RejectedExecutionHandler handler) {
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(30,80,5, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(200), Executors.defaultThreadFactory());
    }
}
/*
   1.Executors实现
   线程池：底层新建ThreadPoolExecutor,ScheduledThreadPoolExecutor对象，通过不同传参，来生成不同的线程池对象：缓存线程池，固定大小的线程池，周期调度线程池
   直接使用ThreadPoolExecutor创建线程池，可自定义核心线程数量，最大线程数量，线程
   2.ThreadPoolExecutor使用说明
    *     public ThreadPoolExecutor(int corePoolSize,  核心线程数： 有新任务提交时，当线程数小于corePoolSize,会创建新线程执行任务
         int maximumPoolSize,                     最大线程数：有新任务提交时，当线程数大于corePoolSize，小于maximunPoolSize时，会使用空闲线程，若没有空闲线程，则创建新线程。
         long keepAliveTime,               当线程数大于corePoolSize时，空闲线程在终止前最多keepAliveTime的时间等待新任务。
         TimeUnit unit,                    keepAliveTime的单位
         BlockingQueue<Runnable> workQueue,    工作队列，有新任务提交时，当线程数等于maximumPoolSize时，任务队列放入workQueue
         ThreadFactory threadFactory,            线程工厂
         RejectedExecutionHandler handler) {       拒绝执行使用的handler
         */
