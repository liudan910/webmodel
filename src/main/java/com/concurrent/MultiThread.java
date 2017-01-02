package com.concurrent;

import com.util.RandonUTil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 1. threadLocal
   模拟用户登录，保存用户信息
 */
class CThread extends Thread{
    private String name;
    public CThread(String name){

    }
    @Override
    public void run(){

    }
}
public class MultiThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }
    public static void test1() throws ExecutionException, InterruptedException {
        for(int i = 0;i<100;i++){
            CThread cThread = new CThread(RandonUTil.getRandomKey());
        }
    }
}