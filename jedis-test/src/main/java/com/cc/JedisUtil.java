package com.cc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: JedisUtil
 * Description: JedisUtil
 * Date:  2018/7/11
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class JedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    private JedisUtil(){}

    //单例模式
    private static class RedisUtilHolder{
        private static final JedisUtil instance = new JedisUtil();
    }

    public static JedisUtil getInstance(){
        return RedisUtilHolder.instance;
    }

    private static Map<String,JedisPool> maps = new HashMap<String,JedisPool>();

    private static JedisPool getPool(String ip, int port){
        String key = ip+":"+port;
        JedisPool pool = null;
        if(!maps.containsKey(key))
        {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(RedisConfig.MAX_TOTAL);
            config.setMaxIdle(RedisConfig.MAX_IDLE);
            config.setMinIdle(RedisConfig.MIN_IDLE);
            config.setMaxWaitMillis(RedisConfig.MAX_WAIT_MILLIS);
            config.setTestOnBorrow(RedisConfig.TEST_ON_BORROW);
            config.setTestOnReturn(true);

            pool = new JedisPool(config,ip,port,RedisConfig.TIMEOUT);
            maps.put(key, pool);
        }
        else
        {
            pool = maps.get(key);
        }
        return pool;
    }

    public Jedis getJedis(String ip, int port)
    {
        Jedis jedis = null;
        int count = 0;
        do
        {
            try
            {
                jedis = getPool(ip,port).getResource();
            }
            catch (Exception e)
            {
                logger.error("get redis master1 failed!",e);
                getPool(ip,port).returnBrokenResource(jedis);
            }
        }
        while(jedis == null && count<RedisConfig.RETRY_NUM);
        return jedis;
    }

    public void closeJedis(Jedis jedis, String ip, int port){
        if(jedis != null)
        {
            getPool(ip,port).returnResource(jedis);
        }
    }

}
