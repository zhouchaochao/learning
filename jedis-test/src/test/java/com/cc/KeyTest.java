package com.cc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Title: KeyTest
 * Description: KeyTest
 * Date:  2018/7/11
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class KeyTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(KeyTest.class);

    @Test
    public void testKey() throws Exception {

        //System.out.println("清空数据："+jedis.flushDB());
        System.out.println("判断某个键是否存在：" + jedis.exists("username"));
        System.out.println("新增<'username','zzh'>的键值对：" + jedis.set("username", "zzh"));
        System.out.println("新增 username 后是否存在：" + jedis.exists("username"));

        System.out.print("系统中所有的键如下：");
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        System.out.println("新增<'password','123456'>的键值对：" + jedis.set("password", "123456"));
        System.out.println("新增 password 后是否存在：" + jedis.exists("password"));
        System.out.println("删除键password:" + jedis.del("password"));
        System.out.println("判断键password是否存在：" + jedis.exists("password"));

        System.out.println("设置键username的过期时间为5s:" + jedis.expire("username", 5));
        Thread.sleep(2000);
        System.out.println("查看键username的剩余生存时间：" + jedis.ttl("username"));
        System.out.println("移除键username的生存时间：" + jedis.persist("username"));
        System.out.println("查看键username的剩余生存时间：" + jedis.ttl("username"));

        System.out.println("查看键username所存储的值的类型：" + jedis.type("username"));

        System.out.println("设置键username的过期时间为2s:" + jedis.expire("username", 2));
        Thread.sleep(3000);
        System.out.println("超出过期时间后 username 后是否存在：" + jedis.exists("username"));
    }

}
