package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title: MapIntegerKeyTest
 * Description: MapIntegerKeyTest
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/9/8
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class MapIntegerKeyTest {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] agrs) {

        Map<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1,"11");
        map.put(2,"22");
        map.put(3,"33");
        map.put(10000,"10000");
        map.put(200000000,"200000000");
        map.put(300000000,"300000000");



        if(map.containsKey(1)){
            logger.info("有 1");
            map.remove(1);
        }

        if(map.containsKey(new Integer(2))){
            logger.info("有 Integer(2)");
            map.remove(new Integer(2));
        }

        if(map.containsKey(new Integer(200000000))){
            logger.info("有  Integer(200000000");
            map.remove(new Integer(200000000));
        }

        if(map.containsKey(300000000)){
            logger.info("有 300000000");
            map.remove(300000000);
        }


        Person person = new Person("cc",25);
        map.remove(person.getAge());

        map.forEach((k,v)->{logger.info(k + " " + v);});

        int i = 10000000;
        Integer integer = new Integer(10000000);
        if(i == integer){
            logger.info("int == Integer()");
        }

        int i2 = 10000000;
        if(i == i2){
            logger.info("int == int ");
        }

        Integer integer2 = new Integer(10000000);
        if(integer != integer2){
            logger.info("Integer != Integer");
        }

        if(new Integer(1) != new Integer(1)){
            logger.info("Integer(1) != Integer(1)");
        }

    }

}
