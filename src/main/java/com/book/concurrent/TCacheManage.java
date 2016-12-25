package com.book.concurrent;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.System.out;

/**
 * Created by czc on 2016/12/25.
 */
public class TCacheManage {
    private Cache<String,String> cache;
    public TCacheManage(){
        CacheBuilder builder = CacheBuilder.newBuilder();
        builder.concurrencyLevel(Runtime.getRuntime().availableProcessors());
        builder.maximumSize(1000);
        builder.weakValues();
        builder.initialCapacity(200);
        cache = builder.build();
    }
    public String get(String key){
        return cache.getIfPresent(key);
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
        out.println(cache.asMap());
        cache.put(key,v.toString());
    }
    public ConcurrentMap<String, String> getAll(){
        ConcurrentHashMap map = new ConcurrentHashMap(cache.asMap());
        return map;
    }

}
