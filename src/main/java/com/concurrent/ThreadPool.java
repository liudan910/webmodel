package com.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by czc on 2016/12/25.
 */
class CTread extends Thread{
    @Override
    public void run(){

    }
}
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
        executor.execute(new Runnable() {
            public void run() {

            }
        });
        CTread cTread = new CTread();
        executor.execute(cTread);
    }
}
/*
   1.Executors实现
   线程池：底层新建ThreadPoolExecutor,ScheduledThreadPoolExecutor对象，通过不同传参，来生成不同的线程池对象：缓存线程池，固定大小的线程池，周期调度线程池
   直接使用ThreadPoolExecutor创建线程池，可自定义核心线程数量，最大线程数量，线程
   2.ThreadPoolExecutor使用说明
    *     public ThreadPoolExecutor(int corePoolSize,  核心线程数： 有新任务提交时，当线程数小于corePoolSize,会创建新线程,新线程以该任务作为第一个任务。
         int maximumPoolSize,                     最大线程数：。
         long keepAliveTime,               当线程数大于corePoolSize时，空闲线程在终止前最多keepAliveTime的时间等待新任务。
         TimeUnit unit,                    keepAliveTime的单位
         BlockingQueue<Runnable> workQueue,    工作队列，有新任务提交时，当线程数等于maximumPoolSize时，任务队列放入workQueue
         ThreadFactory threadFactory,            线程工厂
         RejectedExecutionHandler handler) {       拒绝执行使用的handler

注意： 区分 工作队列 与任务队列

3. ThreadPoolExecutor.execute(Runnable runnable) 实现：
         有新任务提交时，
          1. 当线程数小于corePoolSize,  代码：addWorker(firstTask: command, core: true)) ：
                 会创建新线程,并以该任务作为第一个任务，新线程加入工作集，然后调用新线程.start() 。
               《工作集works： private final HashSet<Worker> workers = new HashSet<Worker>();  赋值写死》
          2. 当线程数大于corePoolSize时，则将该任务加入任务队列，代码：isRunning(c) && workQueue.offer(command)。
               若加入成功，则双重检查线程池状态：
                    如果过线程池非运行状态，则移除该任务并拒绝执行，
                    否则再次查看线程数，（有可能从进入方法到当前，已有一个线程死亡），如小于总容量则 则开始一个新线程。 addWorker(null, false);
               《任务队列workQueue:private final BlockingQueue<Runnable> workQueue;  由程序员new初始化》

            《解释： 当线程数大于corePoolSize时，将任务放入任务队列。》

          3.当任务无法加入任务队列，代码： addWorker(command, false))：创建新线程来处理任务）。
           4.若创建新线程会使 当前运行线程数超出 maximumPoolSize，则拒绝执行。

           注意： addWorkder(firstTask,core): 其中firstTask为新建线程的第一个参数，core为true,则以corePoolSize为边界， 为false，则以maximumPoolSize为边界


            第2步对应：

           《   加入工作队列 即调用addQueue()，而 线程池已有线程的run方式见 为
           Work.run();{ runWork(this);};
           ThreadPoolExecutor..runWork(Work w){          //Work为工作线程，持有一个线程Thread,和 一个FirstTask
                task = w.firstTask;
                w.firstTask = null;
                 while (task != null || (task = getTask()) != null) {             //主循环： 传入的工作线程 w 首先 执行 其自身持有的 firstTask任务，然后从任务队列中取任务执行。
                           ……
                           task.run();
                           ……

                }
           }
           》
*/
