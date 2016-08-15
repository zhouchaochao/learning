package com.jd.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: com.jd.hello.MainClass
 * Description: com.jd.hello.MainClass
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/13
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class MainClass {

    private static Logger logger = LoggerFactory.getLogger(MainClass.class);


    public static void main(String[] agrs) {

        String myName = PropertyUtil.getProperties("myname");

        for (int i = 0; i < 100; i++) {

            logger.info("myname:" + myName + " times:" + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
