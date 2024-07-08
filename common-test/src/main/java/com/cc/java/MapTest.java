package com.cc.java;

import com.alibaba.fastjson.JSON;
import com.sun.tools.javac.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: MapTest
 * @date: 2024-03-06
 */

public class MapTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapTest.class);

    public static void main(String[] args) {
        new MapTest().mergeMapTest();
        new MapTest().mergeMapTest2();
    }

    private void mergeMapTest() {
        Map<String,Integer> countMap = new HashMap<>();
        countMap.put("China",2);
        countMap.put("America",3);

        // one是map中已经存在的值，two是新增加的值，也就是merge函数的第二个参数，这里的two都是1
        countMap.merge("China",1, (one,two) -> one + two);
        countMap.merge("China",1, (one,two) -> one + two);
        countMap.merge("Japan",1, (one,two) -> one + two);
        countMap.merge("Japan",1, (one,two) -> one + two);
        // 输出：{"Japan":2,"China":4,"America":3}

        String movieName = "";
        Map<String,Integer> moviesToCount = new HashMap<>();
        moviesToCount.put(movieName, Optional.ofNullable(moviesToCount.get(movieName)).map(existValue -> existValue + 1).orElse(1));

        if (moviesToCount.get(movieName) == null) {
            moviesToCount.put(movieName, 1);
        } else {
            moviesToCount.put(movieName, moviesToCount.get(movieName) + 1);
        }



        /*//不对
        countMap.merge("China",1, (key,count) -> count + 1);
        countMap.merge("China",1, (key,count) -> count + 1);
        countMap.merge("Japan",1, (key,count) -> count + 1);
        countMap.merge("Japan",1, (key,count) -> count + 1);
        */

        //countMap.merge("Japan",1, Integer::sum);

        System.out.println(JSON.toJSON(countMap));
    }

    private void mergeMapTest2() {
        Map<String, ArrayList<String>> friendsToMovies = new HashMap<>();
        friendsToMovies.put("b", new ArrayList<>(Arrays.asList("b1")));

        // 如果不存在，初始化一个新数组；往数组中添加元素
        friendsToMovies.computeIfAbsent("a", name -> new ArrayList<>()).add("a1");
        friendsToMovies.computeIfAbsent("a", name -> new ArrayList<>()).add("a2");
        friendsToMovies.computeIfAbsent("b", name -> new ArrayList<>()).add("b2");
        friendsToMovies.computeIfAbsent("b", name -> new ArrayList<>()).add("b3");
        friendsToMovies.computeIfAbsent("c", name -> new ArrayList<>()).add("c1");

        System.out.println(JSON.toJSON(friendsToMovies));
        // {"a":["a1","a2"],"b":["b1","b2","b3"],"c":["c1"]}
    }
}
