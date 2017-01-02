package com.test;

import com.util.RandonUTil;

import java.util.HashMap;

import static java.lang.System.out;

/**
 * Created by liuda on 2017/1/1.
 */
class Inte {
    Integer x;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }
}

public class TestMap {
    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < 10; i++) {
            map.put(i, i * 10);
        }
        HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>(map);
        out.println(map);
        out.println(map2);
    }

    public static void test2() {
        HashMap<String, HashMap<Integer, Integer>> map = new HashMap<String, HashMap<Integer, Integer>>();
        HashMap<Integer, Integer> submap1 = new HashMap<Integer, Integer>();
        submap1.put(1, 10);
        String name = RandonUTil.getRandomKey();
        map.put(name, submap1);

        HashMap<String, HashMap<Integer, Integer>> map2 = new HashMap<String, HashMap<Integer, Integer>>(map);
        out.println(map);
        out.println(map2);

        map.get(name).put(1, 20);
        out.println(map);
        out.println(map2);
    }
}
/*
1.测试1，
    情景：
    map1 = new HashMap<>();               s1;
    map1放入元素                          s2;
    map2 = new HashMap<>(map1);           s3;
   当map1内容发生改变时，map2会改变吗？
   运行结果：
   1.value为基本类型，exp: <Integer,Integer>类型
     改变方式   map1.put(3,35);
   原因：
   s3后：map1与map2里的内容 是 相同引用
    exp: map1 = { <Integer@403,Integer@404>}
        map2 = { <Integer@403,Integer@404> }
   当map1发生修改后，Map1的value引用变了
        map1 = { <Integer@403,Integer@408>}
    而map2不会变。 因为Map.put()方法的底层是以 重新赋引用方式替换value。
    故当map1的value为基本类型时，map1的value值变化对map2无影响。

    2.value为对象，修改对象内容。  <String,Map<String>>类型。
    改变方式：
    String name = map1.get("name");
    map1.get("name").put(name+"_1");
    因为
  exp:  map1 = { <String@403,Map@404>}
        map1 = { <String@403,Map@404>}
    当map1发生上述修改后，Map@404的内容发生了修改，
    而map1,map2里存储的依然是相同引用，
    故当map1的value为非基本类型的对象时，
    将map1.value的引用修改后， 对map2无影响。 ————map1.value 与 map2.value 是不同的引用
    而将map1.value的内容修改后，map2也同样修改了。 ————map1.value 与 map2.value仍是同一个引用。


    重点在于： 在两个map的value的对象引用发生修改  还是所指对象内容（实例域）发生修改。
    总结： 分析修改后，是否仍是同一个引用

 */