package com.jd.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: com.jd.hello.Main
 * Description: com.jd.hello.Main
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/13
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] agrs) {

        String myname = PropertyUtil.getProperties("myname");

        for (int i = 0; i < 100; i++) {

            logger.info("myname:" + myname + " times:" + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
