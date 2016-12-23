package com.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import static java.lang.System.out;

/**
 * Created by liudan19 on 2016/12/23.
 */
public class CacheTest {
    public Cache<String,String> cache = null;
    public CacheTest(){
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
