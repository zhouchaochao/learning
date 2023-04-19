package com.cc.java;


import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: FlapMap
 * @date: 12/14/21
 */

public class FlapMap {

    public static void main(String[] args) {
        String params = "name=cc;age=18;sex=1;country=chn";

        Stream<String> stream = Arrays.stream(params.split(";"));
        //Stream<String> stream = Stream.of(params.split(";"));
        stream.forEach(System.out::println);

        Stream<String> stream1 = Arrays.stream(params.split(";"));
        Stream<String> stream2 = stream1.flatMap(str -> Arrays.stream(str.split("=")));
        stream2.forEach(System.out::println);

        System.out.println(Stream.of("a","b").collect(Collectors.toList()));
    }
}
