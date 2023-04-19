package com.cc.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.java.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: CommonTest
 * @date: 11/5/21
 */

public class CommonTest {

    public static void main(String[] args) {

        JSONObject localData = JSON.parseObject("{\"fee\":1678.0001}");
        JSONObject remoteData = JSON.parseObject("{\"fee\":1678.0001}");

        if (!localData.getDouble("fee").equals(remoteData.getDouble("fee"))) {
            System.out.println("DIFF_FEE");;
        }


        Person person = new Person(1, 1, "cc");
        Object object = person;
        object = person;
        person = (Person) object;
        System.out.println(person);

        object = null;
        List<Person> personList = null;
        personList = (List<Person>) object;
        System.out.println(personList);
        System.out.println(object);

        personList = (List<Person>)null;
        System.out.println(personList);




    }

}
