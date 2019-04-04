package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: StringIntegerLongDoubleTest
 * Description: StringIntegerLongDoubleTest
 * Date:  2018/7/17
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class StringIntegerLongDoubleTest {

    private static final Logger logger = LoggerFactory.getLogger(StringIntegerLongDoubleTest.class);

    public static void main(String[] args) {

        String a="abc";
        String b="abc";
        System.out.println("String equals:" + a.equals(b));//true
        System.out.println("String ==:" + (a==b));//true

        a="abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        b="abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        System.out.println("length long String equals:" + a.equals(b));//true
        System.out.println("length long String ==:" + (a==b));//true

        a = new String("abc");
        b = new String("abc");
        System.out.println("new String equals:" + a.equals(b));//true
        System.out.println("new String ==:" + (a==b));//false

        Integer i1 = 1;
        Integer i2 = 1;
        System.out.println(i1 + " Integer equals:" + i1.equals(i2));//true
        System.out.println(i1 + " Integer ==:" + (i1==i2));//true

        i1 = 127;
        i2 = 127;
        System.out.println(i1 + " Integer equals:" + i1.equals(i2));//true
        System.out.println(i1 + " Integer ==:" + (i1==i2));//true

        i1 = 128;
        i2 = 128;
        System.out.println(i1 + " Integer equals:" + i1.equals(i2));//true
        System.out.println(i1 + " Integer ==:" + (i1==i2));//false TODO 注意 为什么呢？ Integer中有个内部类IntegerCache，缓存-127-128的值，以便自动装箱拆箱，Integer.valueOf(int i)会返回缓存的数据

        i1 = new Integer(1);
        i2 = new Integer(1);
        System.out.println("new Integer equals:" + i1.equals(i2));//true
        System.out.println("new Integer ==:" + (i1==i2));//false


        Long l1 = 1L;
        Long l2 = 1L;
        System.out.println(l1 + " Long equals:" + l1.equals(l2));//true
        System.out.println(l1 + " Long ==:" + (l1==l2));//true

        l1 = 127L;
        l2 = 127L;
        System.out.println(l1 + " Long equals:" + l1.equals(l2));//true
        System.out.println(l1 + " Long ==:" + (l1==l2));//true

        l1 = 128L;
        l2 = 128L;
        System.out.println(l1 + " Long equals:" + l1.equals(l2));//true
        System.out.println(l1 + " Long ==:" + (l1==l2));//false TODO 注意

        Double d1 = 1.0;
        Double d2 = 1.0;
        System.out.println(d1 + " Double equals:" + d1.equals(d2));//true
        System.out.println(d1 + " Double ==:" + (d1==d2));//false

    }

}
