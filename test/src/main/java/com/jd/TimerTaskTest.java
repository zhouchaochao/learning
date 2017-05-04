package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Title: TimerTaskTest
 * Description: TimerTaskTest
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/18
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class TimerTaskTest {

    private static Logger logger = LoggerFactory.getLogger(TimerTaskTest.class);

    public static void main(String[] agrs){
        try {
            logger.info("hello cc");


            Timer timer = new Timer();//只有一个任务在执行

            timer.scheduleAtFixedRate(new MyTimerTask("1"), 4000, 5 * 1000);
            timer.scheduleAtFixedRate(new MyTimerTask("2"), 4000, 5 * 1000);

            Thread.sleep(6000);
            timer.cancel();//已经在执行的任务，执行完之后，才会退出

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static class MyTimerTask extends TimerTask {

        String name = "";

        public MyTimerTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                logger.info(name + " Hello !!");
                Thread.sleep(10000);
                logger.info(name + " bye !!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
