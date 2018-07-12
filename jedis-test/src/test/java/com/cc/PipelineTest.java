package com.cc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Pipeline;

/**
 * Title: PipelineTest
 * Description: PipelineTest
 * Date:  2018/7/12
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class PipelineTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(PipelineTest.class);

    @Test
    public void testPipeline() {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            jedis.set("age1" + i, i + "");
            jedis.get("age1" + i);// 每个操作都发送请求给redis-server
        }
        long end = System.currentTimeMillis();
        System.out.println("unuse pipeline cost:" + (end - start) + "ms");

        for (int i = 0; i < 10000; i++) {
            jedis.del("age1" + i);
        }

        start = System.currentTimeMillis();
        Pipeline p = jedis.pipelined();
        for (int i = 0; i < 10000; i++) {
            p.set("age2" + i, i + "");
            System.out.println(p.get("age2" + i));
        }
        p.sync();// 这段代码获取所有的response
        end = System.currentTimeMillis();
        System.out.println("use pipeline cost:" + (end - start) + "ms");

    }

}
