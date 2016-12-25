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
    public Cache<String, String> cache = null;

    public CacheManager() {
        CacheBuilder builder = CacheBuilder.newBuilder();
        builder.maximumSize(200);
        builder.concurrencyLevel(Runtime.getRuntime().availableProcessors());
        builder.initialCapacity(100);
        builder.weakValues();
        cache = builder.build();
    }

    public synchronized void set(String key, String value) {
        String keyC = cache.getIfPresent(key);
        Integer v;
        if (keyC == null) {
            out.println(key + " 1");
            v = 1;
        } else {
            v = Integer.parseInt(keyC) + 1;
            out.println(key + " " + v);
        }
        cache.put(key, v.toString());
    }

    public String get(String key, String value) {
        return cache.getIfPresent(key);
    }

}

/**
 * 1,.线程安全
 * public synchronized void set(String key,String value){
 * String keyC = cache.getIfPresent(key);
 * Integer v ;
 * if(keyC == null){
 *      out.println(key +" 1");
 *      v = 1;
 * }else{
 *      v = Integer.parseInt(keyC)+1;
 *       out.println(key+ " "+v);
 * }
 * cache.put(key,v.toString());
 * }
 * set方法中 依赖共享变量cache的状态，来修改cache
 * 在多线程环境中，需要加锁. exp: synchronized
 * 尽管com.google.cache实现了内部同步，但外部有“依赖共享变量cache状态来修改”时，外部需要自行实现同步
 * 修改：
 * public  void set(String key,String value)
 * --->
 * public synchronized void set(String key,String value){
 * <p>
 * 如果set方法是以下实现形式：则不需要加锁。
 * public void set(String key,String value){
 *     cache.put(key,value)
 * }
 *
 */



























