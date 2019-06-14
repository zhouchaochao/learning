package com.cc.java;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Title: LamdaMapFunctionUse
 * Description: LamdaMapFunctionUse
 * Date:  2019/6/14
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class LamdaMapFunctionUse {

    private static final Logger logger = LoggerFactory.getLogger(LamdaMapFunctionUse.class);


    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person(1, 1, "a"));
        list.add(new Person(2, 1, "b"));
        list.add(new Person(3, 1, "c"));
        list.add(new Person(4, 2, "d"));
        list.add(new Person(5, 2, "e"));
        list.add(new Person(6, 2, "f"));

        //转list
        List<Integer> sexList = list.stream().mapToInt(person -> person.getSex()).boxed().collect(Collectors.toList());
        System.out.println("sexList:" + JSON.toJSONString(sexList));

        //转set
        Set<Integer> sexSet = list.stream().mapToInt(person -> person.getSex()).boxed().collect(Collectors.toSet());
        System.out.println("sexSet:" + JSON.toJSONString(sexSet));

        //转其他对象
        List<String> nameIdList = list.stream().map(new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return person.getName() + person.getId();
            }
        }).collect(Collectors.toList());
        System.out.println("nameIdList:" + JSON.toJSONString(nameIdList));
    }
}
