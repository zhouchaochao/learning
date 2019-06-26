package com.cc.java;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Title: LamadaGroupBy
 * Description: LamadaGroupBy
 * Date:  2019/6/14
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class LamadaGroupBy {

    private static final Logger logger = LoggerFactory.getLogger(LamadaGroupBy.class);


    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person(1, 1, "a"));
        list.add(new Person(2, 1, "b"));
        list.add(new Person(3, 1, "c"));
        list.add(new Person(4, 2, "d"));
        list.add(new Person(5, 2, "e"));
        list.add(new Person(6, 2, "f"));

        Map<Integer, List<Person>> groupBy = list.stream().collect(Collectors.groupingBy(Person::getSex));
        System.out.println("groupBy:" + JSON.toJSONString(groupBy));

    }
}
