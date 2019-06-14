package com.cc.java;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Title: LamadaListToMap
 * Description: LamadaListToMap 参考:https://www.cnblogs.com/baobeiqi-e/p/9884736.html
 * Date:  2019/6/14
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class LamadaListToMap {

    private static final Logger logger = LoggerFactory.getLogger(LamadaListToMap.class);

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person(1,1,"a"));
        list.add(new Person(2,1,"b"));
        list.add(new Person(3,1,"c"));
        list.add(new Person(4,2,"d"));
        list.add(new Person(5,2,"e"));
        list.add(new Person(6,2,"f"));

        Map<Long,String> idNameMap = list.stream().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idNameMap:" + JSON.toJSONString(idNameMap));

        idNameMap = list.stream().collect(Collectors.toMap(Person::getId, person -> person.getName()));
        System.out.println("这样也可以.idNameMap:" + JSON.toJSONString(idNameMap));

        try {
            Map<Integer,String> setNameMap = list.stream().collect(Collectors.toMap(Person::getSex, Person::getName));
        } catch (IllegalStateException e) {
            logger.error("key 重复了会报错：",e.getMessage());
            //通过指定 (key1, key2) -> key1，如果key 重复了，使用最开始的key
            Map<Integer, String> setNameMap = list.stream().collect(Collectors.toMap(Person::getSex, Person::getName, (key1, key2) -> key1));
            System.out.println("setNameMap:" + JSON.toJSONString(setNameMap));
        }

        Map<Long,Person> idMap = list.stream().collect(Collectors.toMap(Person::getId, person -> person));
        System.out.println("idMap:" + JSON.toJSONString(idMap));

        // Function.identity() 是 person -> person 的简略写法
        idMap = list.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("简略写法 idMap:" + JSON.toJSONString(idMap));

    }

}



