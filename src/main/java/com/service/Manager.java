package com.service;

import com.domain.Message;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liudan19 on 2017/10/31.
 */
@Service
public class Manager {
    List<Consumer> consumerList = new ArrayList<Consumer>();
    static final int MAX_LENGTH = 50;
    /*
    使用final修饰入参，入参的引用不可变，其属性可变。
    public void transport(final Message message){
        message.setLength(100); //通过
        message = new Message(); //编译报错，入参使用final修饰时，引用不可变。
        if(message != null){
            Iterator<Consumer> it = consumerList.iterator();
            int id = 0 ;
            while (it.hasNext()){
                Consumer next = it.next();
                next.consume(id++,message);
            }
        }
    }
    */
    public void transport(Message message){
        if(message != null){
            Iterator<Consumer> it = consumerList.iterator();
            int id = 0 ;
            while (it.hasNext()){
                Consumer next = it.next();
                next.consume(id++,message);
            }
        }
    }
    public boolean register(Consumer consumer){
        if(consumerList.size()>500) {
            System.out.println("消费者数已达上限!");
            return false;
        }
        consumerList.add(consumer);
        return true;
    }
}
