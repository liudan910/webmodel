package com.test;
import  static java.lang.System.out;
/**
 * Created by liuda on 2017/1/1.
 */
class CTask implements Runnable{

    public void run() {
        out.println(this.toString());
    }
}
public class TestThread {
    public static void main(String[] args){
        Thread thread = new Thread(new CTask(),"ttt");
        thread.start();
    }
}
