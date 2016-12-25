package com.book.concurrent;

import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * Created by czc on 2016/12/25.
 */
class UnsafeSequence {
    private int value;

    public int getNext() {
        return value++;
    }

    public int getCurrent() {
        return value;
    }
}

class BThread extends Thread {
    private CountDownLatch latch;
    private UnsafeSequence sequence;

    public BThread(UnsafeSequence sequence, CountDownLatch latch) {
        this.sequence = sequence;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            sleep((int) Math.random() * 10);
            this.sequence.getNext();
        } catch (Exception e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}

class FThread extends Thread {
    private CountDownLatch latch;
    private UnsafeSequence sequence;

    public FThread(UnsafeSequence sequence, CountDownLatch latch) {
        this.sequence = sequence;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            sleep((int) Math.random() * 10);
            this.sequence.getNext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        latch.countDown();
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        int diff = 0;
        for (int j = 0; j < 50; j++) {
            CountDownLatch latch = new CountDownLatch(1000);
            UnsafeSequence sequence = new UnsafeSequence();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 100, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(50), Executors.defaultThreadFactory());

            for (int i = 0; i < 500; i++) {
                executor.execute(new BThread(sequence, latch));
                executor.execute(new FThread(sequence, latch));
            }
            latch.await();
            int current = sequence.getCurrent();
            out.println(current);
            if(current!=1000){
                diff ++;
            }
            executor.shutdownNow();
        }
        out.println("diff:"+diff);
    }
}
/**
 * public ThreadPoolExecutor(int corePoolSize,
 * int maximumPoolSize,
 * long keepAliveTime,
 * TimeUnit unit,
 * BlockingQueue<Runnable> workQueue,
 * ThreadFactory threadFactory,
 * RejectedExecutionHandler handler) {
 */
