package cn.xilio.falcon.job.coe;

import java.util.HashMap;

public class TestAll {
    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        Object o = map.putIfAbsent("1", "2");
        System.out.println(o);//返回的是null
        System.out.println("--");
        Object o1 = map.putIfAbsent("1", "3");//key已经存在了，所以返回的是之前的值 不做任何操作
        System.out.println(o1);//返回的是之前的值 o1=2
    }
}
