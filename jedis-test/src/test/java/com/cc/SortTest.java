package com.cc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.SortingParams;

import java.util.List;

/**
 * Title: SortTest
 * Description: SortTest
 * Date:  2018/7/12
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class SortTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SortTest.class);

    @Test
    public void testSort() {

        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
        System.out.println("collections的内容：" + jedis.lrange("collections", 0, -1));
        SortingParams sortingParameters = new SortingParams();
        System.out.println(jedis.sort("collections", sortingParameters.alpha()));
        System.out.println("collections的内容不变：" + jedis.lrange("collections", 0, -1));

        System.out.println("===============================");
        jedis.lpush("sortedList", "3", "6", "2", "0", "7", "4");
        System.out.println("sortedList排序前：" + jedis.lrange("sortedList", 0, -1));
        System.out.println("升序：" + jedis.sort("sortedList", sortingParameters.asc()));
        System.out.println("降序：" + jedis.sort("sortedList", sortingParameters.desc()));
        System.out.println("sortedList排序后不变：" + jedis.lrange("sortedList", 0, -1));
        jedis.sort("sortedList", sortingParameters.asc(), "sortedList");// 排序后指定排序结果到一个KEY中，这里讲结果覆盖原来的KEY
        System.out.println("sortedList排序覆盖后：" + jedis.lrange("sortedList", 0, -1));


        System.out.println("===============================");
        jedis.lpush("userlist", "33");
        jedis.lpush("userlist", "22");
        jedis.lpush("userlist", "55");
        jedis.lpush("userlist", "11");

        jedis.hset("user:66", "name", "66");
        jedis.hset("user:55", "name", "55");
        jedis.hset("user:33", "name", "33");
        jedis.hset("user:22", "name", "79");
        jedis.hset("user:11", "name", "24");
        jedis.hset("user:11", "add", "beijing");
        jedis.hset("user:22", "add", "shanghai");
        jedis.hset("user:33", "add", "guangzhou");
        jedis.hset("user:55", "add", "chongqing");
        jedis.hset("user:66", "add", "xi'an");
        sortingParameters = new SortingParams();
        sortingParameters.get("user:*->name");
        sortingParameters.get("user:*->add");
        //sortingParameters.by("user:*->name");
        sortingParameters.get("#");
        System.out.println(jedis.sort("userlist", sortingParameters));//TODO 什么意思


        System.out.println("===============================");
        jedis.sadd("tom:friend:list", "123"); // tom的好友列表
        jedis.sadd("tom:friend:list", "456");
        jedis.sadd("tom:friend:list", "789");
        jedis.sadd("tom:friend:list", "101");

        jedis.set("score:uid:123", "1000"); // 好友对应的成绩
        jedis.set("score:uid:456", "6000");
        jedis.set("score:uid:789", "100");
        jedis.set("score:uid:101", "5999");

        jedis.set("uid:123", "{'uid':123,'name':'lucy'}"); // 好友的详细信息
        jedis.set("uid:456", "{'uid':456,'name':'jack'}");
        jedis.set("uid:789", "{'uid':789,'name':'jay'}");
        jedis.set("uid:101", "{'uid':101,'name':'jolin'}");

        sortingParameters = new SortingParams();
        sortingParameters.desc();
        sortingParameters.get("#");// GET 还有一个特殊的规则—— "GET #"  ，用于获取被排序对象(我们这里的例子是 user_id )的当前元素。
        sortingParameters.by("score:uid:*");
        System.out.println("tom:friend:list排序：" + jedis.sort("tom:friend:list", sortingParameters));


        sortingParameters = new SortingParams();
        sortingParameters.desc();
        // sortingParameters.limit(0, 2);
        // 注意GET操作是有序的，GET user_name_* GET user_password_*
        // 和 GET user_password_* GET user_name_*返回的结果位置不同
        sortingParameters.get("#");// GET 还有一个特殊的规则—— "GET #"  ，用于获取被排序对象(我们这里的例子是 user_id )的当前元素。
        sortingParameters.get("uid:*");
        sortingParameters.get("score:uid:*");
        sortingParameters.by("score:uid:*");
        // 对应的redis 命令是./redis-cli sort tom:friend:list by score:uid:* get # get
        // uid:* get score:uid:*
        System.out.println("tom:friend:list排序：" + jedis.sort("tom:friend:list", sortingParameters));


    }

}
