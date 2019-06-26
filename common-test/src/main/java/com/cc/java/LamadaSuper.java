package com.cc.java;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Title: LamadaGroupBy
 * Description: LamadaGroupBy https://blog.csdn.net/qq_33609401/article/details/84862721
 * Date:  2019/6/14
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class LamadaSuper {

    private static final Logger logger = LoggerFactory.getLogger(LamadaSuper.class);


    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person(1, 1, "a"));
        list.add(new Person(2, 1, "b"));
        list.add(new Person(3, 1, "c"));
        list.add(new Person(4, 2, "d"));
        list.add(new Person(5, 2, "e"));
        list.add(new Person(6, 2, "f"));

        //求和。这个是我猜的
        Long total = list.stream().map(Person::getId).reduce(0l, (key1,key2) -> key1+key2);
        System.out.println("total:"+total);  //totalMoney:17.48

        Optional<Person> maxData = list.stream().
                collect(Collectors.maxBy(Comparator.comparing(Person::getId)));
        System.out.println("max:" + JSON.toJSONString(maxData.get()));

        Optional<Person> minData = list.stream().
                collect(Collectors.minBy(Comparator.comparing(Person::getId)));
        System.out.println("min:" + JSON.toJSONString(minData.get()));

        // 根据sex去重
        List<Person> unique = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(Person::getSex))), ArrayList::new)
        );
        System.out.println("unique:" + JSON.toJSONString(unique));

    }
}
