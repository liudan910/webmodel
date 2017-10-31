package com.service;

import com.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liudan19 on 2017/10/31.
 */
@Service
public class Producer {
    @Autowired
    Manager manager;
    public void sendMessage(Message message){
        manager.transport(message);
    }
}
