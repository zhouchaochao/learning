package com.cc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;

/**
 * Title: pubSubTest
 * Description: pubSubTest
 * Date:  2018/9/4
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class pubSubTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(pubSubTest.class);


    @Test
    public void testPublish() {
        try {
            for (int i = 0; i < 10; i++) {
                String channel = "cc.channel1";
                String message = "hello" + i + new Date();
                jedis.publish(channel, message);
                System.out.println("发送消息:" + channel + " --> " + message);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Test
    public void testSubscribe() {

        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("收到消息:" + channel + " --> " + message);
                super.onMessage(channel, message);
            }
        };

        jedis.subscribe(jedisPubSub, "cc.channel1");

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
