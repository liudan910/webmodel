package com.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

/**
 * Created by liudan19 on 2016/12/23.
 */
@Component
public class CThread extends Thread{
        @Autowired
        CacheManager cacheManager;

        //CacheTest test = new CacheTest();
        @Override
        public void run(){
            System.out.println(this.getId());
            String key = getRandomKey();
            String value = "1";
            cacheManager.set(key,value);
            //test.set(key,value);
        }
        public  String getRandomKey(){
            String sample = "ABCDEFGHIJKLMNOPQRSTUCWXYZ";
            int x = 0;
            while((x=getRandom())<2){
            }
            return sample.substring(x-2,x);
        }
        public int getRandom(){
            return (int)(Math.random()*26);
        }
}
