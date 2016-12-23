package com.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import static java.lang.System.out;
/**
 * Created by liudan19 on 2016/12/23.
 */
@Component
public class CacheManager {
    public Cache<String,String> cache = null;
    public CacheManager(){
        CacheBuilder builder = CacheBuilder.newBuilder();
        builder.maximumSize(200);
        builder.concurrencyLevel(Runtime.getRuntime().availableProcessors());
        builder.initialCapacity(100);
        builder.weakValues();
        cache = builder.build();
    }
    public void set(String key,String value){
        String keyC = cache.getIfPresent(key);
        Integer v ;
        if(keyC == null){
            out.println(key +" 1");
            v = 1;
        }else{
            v = Integer.parseInt(keyC)+1;
            out.println(key+ " "+v);
        }

        cache.put(key,v.toString());
    }
    public String get(String key,String value){
        return cache.getIfPresent(key);
    }

}





























