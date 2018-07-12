package com.cc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Title: BaseTest
 * Description: BaseTest
 * Date:  2018/7/11
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BaseTest {

    private static final String ipAddr = "10.94.96.197";
    private static final int port = 6379;
    public static Jedis jedis = null;

    @BeforeClass
    public static void init() {
        jedis = JedisUtil.getInstance().getJedis(ipAddr, port);
    }

    @AfterClass
    public static void close() {
        JedisUtil.getInstance().closeJedis(jedis, ipAddr, port);
    }

}
