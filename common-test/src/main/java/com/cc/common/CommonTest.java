package com.cc.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.java.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: CommonTest
 * @date: 11/5/21
 */

public class CommonTest {

    public static void main(String[] args) {

        Unsafe unsafe = Unsafe.getUnsafe();
        unsafe.allocateMemory(1024);
        unsafe.reallocateMemory(1024, 1024);
        unsafe.freeMemory(1024);

        ByteBuffer buffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);

        String str = ",a,b,";
        System.out.println(JSON.toJSONString(str.split(",")));

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

    // 每n个节点翻转
    private Node revert(Node node, int x){
        if (node == null) {
            return null;
        }

        //找到 [head,...,tail] n个节点
        int i = 1;
        Node head = node;
        Node tail = node;
        while (i < x && tail.next != null) {
            tail = tail.next;
            i++;
        }

        // 长度不足
        if (i < x) {
            return head;
        } else {
            Node nextListHead = revert(tail.next, x);
            // 翻转 [head,...,tail] n个节点，翻转后tail是头，head是尾
            revertTop(head, x);
            head.next = nextListHead;
            return tail;
        }
    }

    private Node revertTop(Node node, int x) {
        int i = 1;
        Node currentHead = node;
        Node nextHead = node.next;
        currentHead.next = null;

        while (i++<x && nextHead != null) {
            Node temp = nextHead.next;
            nextHead.next = currentHead;
            currentHead = nextHead;
            nextHead = temp;
        }
        return currentHead;
    }

    static class Node {
        Node next;
    }

}
