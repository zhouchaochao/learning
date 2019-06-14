package com.cc;

import com.alibaba.fastjson.JSON;
import com.cc.distribute.cache.FormCache;
import com.cc.distribute.cache.InvokeEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Title: SortedSetTest
 * Description: SortedSetTest
 * Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
 * 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
 * 有序集合的成员是唯一的,但分数(score)却可以重复。
 * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
 * Date:  2018/7/12
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class SortedSetTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SortedSetTest.class);

    @Test
    public void testSortedSet() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("key2", 1.2);
        map.put("key3", 4.0);
        map.put("key4", 5.0);
        map.put("key5", 0.2);
        System.out.println(jedis.zadd("zset", 3, "key1"));
        System.out.println(jedis.zadd("zset", map));
        System.out.println("zset中的所有元素：" + jedis.zrange("zset", 0, -1));
        System.out.println("zset中的所有元素：" + jedis.zrangeWithScores("zset", 0, -1));
        System.out.println("zset中的所有元素：" + jedis.zrangeByScore("zset", 0, 100));
        System.out.println("zset中的所有元素：" + jedis.zrangeByScoreWithScores("zset", 0, 100));
        System.out.println("zset中key2的分值：" + jedis.zscore("zset", "key2"));
        System.out.println("zset中key2的排名：" + jedis.zrank("zset", "key2"));
        System.out.println("删除zset中的元素key3：" + jedis.zrem("zset", "key3"));
        System.out.println("zset中的所有元素：" + jedis.zrange("zset", 0, -1));
        System.out.println("zset中元素的个数：" + jedis.zcard("zset"));
        System.out.println("zset中分值在1-4之间的元素的个数：" + jedis.zcount("zset", 1, 4));
        System.out.println("key2的分值加上5：" + jedis.zincrby("zset", 5, "key2"));
        System.out.println("key3的分值加上4：" + jedis.zincrby("zset", 4, "key3"));
        System.out.println("zset中的所有元素：" + jedis.zrange("zset", 0, -1));
        System.out.println("zset中的所有元素：" + jedis.zrangeWithScores("zset", 0, -1));
        long num = jedis.zremrangeByScore("zset", "-inf", "3.0");
        System.out.println("删除个数：" + num);
        System.out.println("zset中的所有元素：" + jedis.zrange("zset", 0, -1));

    }


    @Test
    public void testSortedSetExist() throws Exception{

        String key = "updatedFormId";

        System.out.println("是否存在:" + jedis.exists(key));
        System.out.println(jedis.zadd(key, 10, "shi"));
        System.out.println(jedis.zadd(key, 15, "shiwu"));
        System.out.println(jedis.zadd(key, 20, "ershi"));
        System.out.println("所有元素：" + jedis.zrange(key, 0, -1));
        System.out.println("所有元素zrangeWithScores：" + jedis.zrangeWithScores(key, 0, -1));
        System.out.println("15-20元素：" + jedis.zrangeByScore(key, 15, 20));

        System.out.println("删除一个元素：" + jedis.zrem(key, "shiwu"));
        System.out.println("所有元素：" + jedis.zrange(key, 0, -1));

        System.out.println("按照分数删除：" + jedis.zremrangeByScore(key, 0, 15));
        System.out.println("所有元素：" + jedis.zrange(key, 0, -1));

        System.out.println("删除：" + jedis.del(key));
        System.out.println("是否存在:" + jedis.exists(key));
    }

    @Test
    public void abc() throws Exception{

        FormCache formCache = new FormCache();
        formCache.setInvalidate("10",2l);

        System.out.println(this.getClass().getName() + ":"
                + Thread.currentThread() .getStackTrace()[1].getMethodName());

        String className = "com.cc.distribute.cache.FormCache";
        String methodName = "setInvalidate";
        String argument1 = "10";
        Long argument2 = 20l;
        Class clazz1 = String.class;
        Class clazz2 = Long.class;

        InvokeEntity invokeEntity = new InvokeEntity();
        invokeEntity.setClassName(className);
        invokeEntity.setMethodName(methodName);
        invokeEntity.addParam(argument1);
        invokeEntity.addParam(argument2);
        //invokeEntity.addParamsType(clazz1);
        //invokeEntity.addParamsType(clazz2);

        String jsonStr = JSON.toJSONString(invokeEntity);
        System.out.println(jsonStr);

        InvokeEntity invokeEntityNew = JSON.parseObject(jsonStr,InvokeEntity.class);
        String jsonStrNew = JSON.toJSONString(invokeEntityNew);
        System.out.println(jsonStrNew);

        Class<?> demo = null;
        try {
            demo = Class.forName(invokeEntityNew.getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class[] parasClassArr = new Class[invokeEntityNew.getParamsType().size()];
            invokeEntityNew.getParamsType().toArray(parasClassArr);
            Object[] parasArr = invokeEntityNew.getParamsValue().toArray();
            for (int i = 0; i < parasClassArr.length; i++) {
                if (parasClassArr[i].equals(String.class)) {
                    parasArr[i] = String.valueOf(parasArr[i]);
                } else if (parasClassArr[i].equals(Long.class)) {
                    parasArr[i] = Long.valueOf(parasArr[i].toString());
                } else if (parasClassArr[i].equals(Double.class)) {
                    parasArr[i] = Double.valueOf(parasArr[i].toString());
                } else if (parasClassArr[i].equals(Integer.class)) {
                    parasArr[i] = Integer.valueOf(parasArr[i].toString());
                } else if (parasClassArr[i].equals(Short.class)) {
                    parasArr[i] = Short.valueOf(parasArr[i].toString());
                } else if (parasClassArr[i].equals(Boolean.class)) {
                    parasArr[i] = Boolean.valueOf(parasArr[i].toString());
                }
            }
            Method method = demo.getMethod(invokeEntityNew.getMethodName(),parasClassArr);
            method.invoke(demo.newInstance(),parasArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
