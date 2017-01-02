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
 * ����һ
 ������
 1.�̳߳�ִ����EventExecutor���ڲ��� ������������
 2.service���ڴ���ҵ���Զ�ע��EventExecutor
 3.ģ��web�������У�ÿ������һ���̣߳���service����
 4.EventExecutor �� service������Ϊbean,������ģʽ
 ���ԣ�
 �̳߳�ִ�����е� ���� �� ��������ģ�������������ģ�
 ����
 Client ģ�� �ͻ��ˣ� ���Ϸ�������
 Serverģ�����ˣ����Ͻ�������ÿ�����󶼻���� DynamicService������
    DynamicService�� ����EventExecutor�������������νӿ�
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
        //������ֻ��һ��
        threads = new RequestThread [10];
        String[] iNames = {"static","dynamic","picture"};
        for(int i = 0;i<10;i++){        //10������
            String interfaceName = iNames[i%3];
            threads[i] = new RequestThread (interfaceName,server,latch); //���������������
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
        client .webserver();     //��������
        LookThread lookThread = new LookThread(server,latch);
        Thread thread = new Thread(lookThread);
        thread.start();
        latch.countDown();                      //ʹ������ �� �����̳߳ص��߳�ͬʱ��ʼ
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
 * �����̳߳ص��߳�
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
        out.println("live���߳���(��̬�ģ�");
        int activeCount = 0;
        while(Runnable) {
            activeCount = server.dynamicService.eventExecutor.getActiveCount();
            if(activeCount>0){
                out.println(activeCount);
            }
        }
        out.println("���������:");
        out.println(server.dynamicService.eventExecutor.getCompletedTaskCount());
    }
}
/**
 ���Խ�� �� �̳߳��У�
    �����߳����� 0-1
    ���������:50

 ʹ��ͬһ��ִ�������ύ��50�����񣬵�ֻ����һ���̡߳�
�᣺
 ���̳߳�ִ�����ǵ����ģ����������̶߳�ʹ��ͬһ��ִ����������ִ��������˵��
 �̳߳�ִ������ ������ = ������ * ��������νӿ�
�ʶ�SOA��˵��
   �̺߳������� 90�����240�� ������г��ȣ�500
 ��ִ������˵�������� = SOA�ύ����������
               �߳�ʵ������ �� ִ�����ڲ�����


 */