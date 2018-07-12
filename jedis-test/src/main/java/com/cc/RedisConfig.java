package com.cc;

/**
 * Title: RedisConfig
 * Description: RedisConfig
 * Date:  2018/7/11
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class RedisConfig {

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    public static int MAX_ACTIVE = 1024;

    //jedis池最大连接数总数，默认8
    public static int MAX_TOTAL = 50;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    public static int MAX_IDLE = 200;

    //jedis池最少空闲连接数
    public static int MIN_IDLE = 10;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    public static int MAX_WAIT_MILLIS = 10000;

    public static int TIMEOUT = 10000;

    public static int RETRY_NUM = 5;

    //在borrow一个jedis实例时，是否提前进行validate操作
    public static boolean TEST_ON_BORROW = true;

}
