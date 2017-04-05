package com.service;

import java.util.Map;

/**
 * Created by liuda on 2017/4/6.
 */
public interface ChineseInter {
        public Map<Long, Map<String, String>> queryProductBase();
        public String sayHello(String name);
}
