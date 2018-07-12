package com.cc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: NumberTest
 * Description: NumberTest
 * Date:  2018/7/11
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class NumberTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(NumberTest.class);

    @Test
    public void testNumber() {

        jedis.set("key1", "1");
        jedis.set("key2", "2");
        jedis.set("key3", "2.3");
        System.out.println("key1的值：" + jedis.get("key1"));
        System.out.println("key2的值：" + jedis.get("key2"));
        System.out.println("key3的值：" + jedis.get("key3"));
        System.out.println("key1的值加1：" + jedis.incr("key1"));
        System.out.println("获取key1的值：" + jedis.get("key1"));
        System.out.println("key2的值减1：" + jedis.decr("key2"));
        System.out.println("获取key2的值：" + jedis.get("key2"));
        System.out.println("将key1的值加上整数5：" + jedis.incrBy("key1", 5));
        System.out.println("获取key1的值：" + jedis.get("key1"));
        System.out.println("将key2的值减去整数5：" + jedis.decrBy("key2", 5));
        System.out.println("获取key2的值：" + jedis.get("key2"));

    }

}
