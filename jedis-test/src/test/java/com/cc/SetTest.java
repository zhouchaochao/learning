package com.cc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Title: SetTest
 * Description: SetTest
 * Date:  2018/7/12
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class SetTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SetTest.class);

    @Test
    public void testSet() {

        System.out.println("============向集合中添加元素============");
        System.out.println(jedis.sadd("eleSet", "e1", "e2", "e4", "e3", "e0", "e8", "e7", "e5"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println("eleSet的所有元素为：" + jedis.smembers("eleSet"));
        System.out.println("删除一个元素e0：" + jedis.srem("eleSet", "e0"));
        System.out.println("eleSet的所有元素为：" + jedis.smembers("eleSet"));
        System.out.println("删除两个元素e7和e6：" + jedis.srem("eleSet", "e7", "e6"));
        System.out.println("eleSet的所有元素为：" + jedis.smembers("eleSet"));
        System.out.println("随机的移除集合中的一个元素：" + jedis.spop("eleSet"));
        System.out.println("随机的移除集合中的一个元素：" + jedis.spop("eleSet"));
        System.out.println("eleSet的所有元素为：" + jedis.smembers("eleSet"));
        System.out.println("eleSet中包含元素的个数：" + jedis.scard("eleSet"));
        System.out.println("e3是否在eleSet中：" + jedis.sismember("eleSet", "e3"));
        System.out.println("e1是否在eleSet中：" + jedis.sismember("eleSet", "e1"));
        System.out.println("e1是否在eleSet中：" + jedis.sismember("eleSet", "e5"));
        System.out.println("=================================");
        System.out.println(jedis.sadd("eleSet1", "e1", "e2", "e4", "e3", "e0", "e8", "e7", "e5"));
        System.out.println(jedis.sadd("eleSet2", "e1", "e2", "e4", "e3", "e0", "e8"));
        System.out.println("将eleSet1中删除e1并存入eleSet3中：" + jedis.smove("eleSet1", "eleSet3", "e1"));
        System.out.println("将eleSet1中删除e2并存入eleSet3中：" + jedis.smove("eleSet1", "eleSet3", "e2"));
        System.out.println("eleSet1中的元素：" + jedis.smembers("eleSet1"));
        System.out.println("eleSet3中的元素：" + jedis.smembers("eleSet3"));
        System.out.println("============集合运算=================");
        System.out.println("eleSet1中的元素：" + jedis.smembers("eleSet1"));
        System.out.println("eleSet2中的元素：" + jedis.smembers("eleSet2"));
        System.out.println("eleSet1和eleSet2的交集:" + jedis.sinter("eleSet1", "eleSet2"));
        System.out.println("eleSet1和eleSet2的并集:" + jedis.sunion("eleSet1", "eleSet2"));
        System.out.println("eleSet1和eleSet2的差集:" + jedis.sdiff("eleSet1", "eleSet2"));//eleSet1中有，eleSet2中没有

    }




    @Test
    public void testSetExpire() throws Exception{

        String key = "updatedFormId0249";
        System.out.println("key不存在时。所有元素为：" + jedis.smembers(key));

        //System.out.println("设置过期时间为5s:" + jedis.expire(key, 5));
        System.out.println("是否存在:" + jedis.exists(key));
        System.out.println("设置过期时间为5s:" + jedis.expire(key, 5));
        System.out.println("设置过期时间后 是否存在:" + jedis.exists(key));

        System.out.println("============向集合中添加元素============");
        System.out.println(jedis.sadd(key, "1"));
        System.out.println(jedis.sadd(key, "3"));

        try {
            System.out.println("当做字符串获取：" + jedis.get(key));
        } catch (JedisDataException e) {
            logger.error(e.getMessage(), e);
        }

        System.out.println("包含元素的个数：" + jedis.scard(key));

        System.out.println("过期时间：" + jedis.ttl(key));

        System.out.println("设置过期时间为5s:" + jedis.expire(key, 5));
        Thread.sleep(2000);
        System.out.println("2000后查看剩余生存时间：" + jedis.ttl(key));
        //System.out.println("移除键username的生存时间：" + jedis.persist(key));
        //System.out.println("查看剩余生存时间：" + jedis.ttl(key));
        Thread.sleep(4000);
        System.out.println("6000后查看剩余生存时间：" + jedis.ttl(key));
        System.out.println("删除：" + jedis.del(key));
        System.out.println("是否存在:" + jedis.exists(key));
    }

}
