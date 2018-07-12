package com.cc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: StringTest
 * Description: StringTest
 * Date:  2018/7/11
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class StringTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(StringTest.class);

    @Test
    public void testString() throws Exception {

        System.out.println("===========增加数据===========");
        System.out.println(jedis.set("key1", "value1"));
        System.out.println(jedis.set("key2", "value2"));
        System.out.println(jedis.set("key3", "value3"));
        System.out.println("删除键key2:" + jedis.del("key2"));
        System.out.println("获取键key2:" + jedis.get("key2"));
        System.out.println("修改key1:" + jedis.set("key1", "value1Changed"));
        System.out.println("获取key1的值：" + jedis.get("key1"));
        System.out.println("在key3后面加入值：" + jedis.append("key3", "End"));
        System.out.println("key3的值：" + jedis.get("key3"));
        System.out.println("增加多个键值对：" + jedis.mset("key01", "value01", "key02", "value02", "key03", "value03"));
        System.out.println("获取多个键值对：" + jedis.mget("key01", "key02", "key03"));
        System.out.println("获取多个键值对：" + jedis.mget("key01", "key02", "key03", "key04"));
        System.out.println("删除多个键值对：" + jedis.del(new String[]{"key01", "key02"}));
        System.out.println("获取多个键值对：" + jedis.mget("key01", "key02", "key03"));


        System.out.println("===========新增键值对防止覆盖原先值==============");
        System.out.println("获取key1的值：" + jedis.get("key1"));
        System.out.println(jedis.setnx("key1", "value1"));
        System.out.println("获取key2的值：" + jedis.get("key2"));
        System.out.println(jedis.setnx("key2", "value2"));
        System.out.println(jedis.setnx("key2", "value2-new"));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("key2"));


        System.out.println("===========新增键值对并设置有效时间=============");
        System.out.println(jedis.setex("key3", 2, "value3"));
        System.out.println(jedis.get("key3"));
        Thread.sleep(3000);
        System.out.println("过期后获取数据：" + jedis.get("key3"));


        System.out.println("===========获取原值，更新为新值==========");//GETSET is an atomic set this value and return the old value command.
        System.out.println(jedis.getSet("key2", "key2GetSet"));
        System.out.println(jedis.get("key2"));

        System.out.println("获得key2的值的字串截取：" + jedis.getrange("key2", 2, 4));

    }

}
