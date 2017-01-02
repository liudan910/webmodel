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
1.����1��
    �龰��
    map1 = new HashMap<>();               s1;
    map1����Ԫ��                          s2;
    map2 = new HashMap<>(map1);           s3;
   ��map1���ݷ����ı�ʱ��map2��ı���
   ���н����
   1.valueΪ�������ͣ�exp: <Integer,Integer>����
     �ı䷽ʽ   map1.put(3,35);
   ԭ��
   s3��map1��map2������� �� ��ͬ����
    exp: map1 = { <Integer@403,Integer@404>}
        map2 = { <Integer@403,Integer@404> }
   ��map1�����޸ĺ�Map1��value���ñ���
        map1 = { <Integer@403,Integer@408>}
    ��map2����䡣 ��ΪMap.put()�����ĵײ����� ���¸����÷�ʽ�滻value��
    �ʵ�map1��valueΪ��������ʱ��map1��valueֵ�仯��map2��Ӱ�졣

    2.valueΪ�����޸Ķ������ݡ�  <String,Map<String>>���͡�
    �ı䷽ʽ��
    String name = map1.get("name");
    map1.get("name").put(name+"_1");
    ��Ϊ
  exp:  map1 = { <String@403,Map@404>}
        map1 = { <String@403,Map@404>}
    ��map1���������޸ĺ�Map@404�����ݷ������޸ģ�
    ��map1,map2��洢����Ȼ����ͬ���ã�
    �ʵ�map1��valueΪ�ǻ������͵Ķ���ʱ��
    ��map1.value�������޸ĺ� ��map2��Ӱ�졣 ��������map1.value �� map2.value �ǲ�ͬ������
    ����map1.value�������޸ĺ�map2Ҳͬ���޸��ˡ� ��������map1.value �� map2.value����ͬһ�����á�


    �ص����ڣ� ������map��value�Ķ������÷����޸�  ������ָ�������ݣ�ʵ���򣩷����޸ġ�
    �ܽ᣺ �����޸ĺ��Ƿ�����ͬһ������

 */