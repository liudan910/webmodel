package com.book.concurrent.producer.consumer;

import com.util.RandonUTil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liuda on 2017/1/2.
 * 使用生产者消费者模式 实现
 */
class Producer extends Thread{
    @Autowired
    MessageDelivery messageDelivery;
    @Override
    public void run(){
        String topic = RandonUTil.getRandomKey();
        messageDelivery.commit(topic);
    }
}
@Component
public class MessageDelivery {
    public void commit(String topic){

    }
    public void sendMessage(Message message){

    }
    public static void main(String[] args){

    }
}
class Message{
    String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}