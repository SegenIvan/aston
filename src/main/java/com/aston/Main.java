package com.aston;

import com.aston.task1.CoolHashMap;

public class Main {
    public static void main(String[] args) {
        CoolHashMap<Integer, String> map = new CoolHashMap<>();

        map.put(1, "test1");
        map.put(2, "test2");
        map.put(3, "test3");

        System.out.println(map.containsKey(1));
        System.out.println(map.get(2));
        System.out.println(map.isEmpty());
        System.out.println(map.size());
        System.out.println(map.remove(2));
        System.out.println(map.get(2));
    }
}
