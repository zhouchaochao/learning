package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Title: ScheduleTask
 * Description: ScheduleTask
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/18
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class ScheduleTask {

    private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();//同一时刻只有一个任务在执行

    private static ScheduledExecutorService service2 = Executors.newScheduledThreadPool(2);//同一时刻只有一个任务在执行




    //Executors.newScheduledThreadPool()

    public static void main(String[] agrs) {
        logger.info("hello cc");

       /* service.scheduleAtFixedRate(new MyTask("1"), 5, 1, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(new MyTask("2"), 5, 1, TimeUnit.SECONDS);*/

/*        service2.scheduleAtFixedRate(new MyTask("1"), 5, 2, TimeUnit.SECONDS);
        service2.scheduleAtFixedRate(new MyTask("2"), 5, 2, TimeUnit.SECONDS);
        service2.scheduleAtFixedRate(new MyTask("3"), 5, 2, TimeUnit.SECONDS);*/

/*        service2.schedule(new MyTask("1"), 5,  TimeUnit.SECONDS);
        service2.schedule(new MyTask("2"), 5,  TimeUnit.SECONDS);
        service2.schedule(new MyTask("3"), 5,  TimeUnit.SECONDS);*/


        ScheduledFuture<String> s = service2.schedule(new MyCallableTask("1"), 5, TimeUnit.SECONDS);

        try {
            //while (true) {
            Thread.sleep(6000);
            s.cancel(true);
            Thread.sleep(2000);
            //logger.info("定时任务执行结果：" + s.get());
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class MyTask implements Runnable {

        String name = "";

        public MyTask(String name) {
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


    static class MyCallableTask implements Callable<String> {

        String name = "";

        public MyCallableTask(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            try {
                logger.info(name + " Hello !!");
                Thread.sleep(10000);
                logger.info(name + " bye !!");
                return name + " finished " + new Date();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
