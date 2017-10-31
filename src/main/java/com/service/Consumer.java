package com.service;

import com.domain.Message;
import org.springframework.stereotype.Service;

/**
 * Created by liudan19 on 2017/10/31.
 */
@Service
public class Consumer {
    public void consume(Integer id,Message message){
        if(message != null) {
            System.out.println("consumer_"+id+",get message: " + message.getContent());
        }
    }
}
