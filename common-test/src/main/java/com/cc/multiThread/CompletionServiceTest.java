package com.cc.multiThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Title: Completion
 * Description: Completion
 * Date:  2019/7/19
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class CompletionServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompletionServiceTest.class);

    private final static ExecutorService BASE_THREAD_POOL = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try {
            new CompletionServiceTest().completionServiceTest();
        } catch (Exception e) {
            LOGGER.error("errorMessage={}", e.getMessage(), e);
        }
    }

    public void completionServiceTest() {
        try {
            long begin = System.currentTimeMillis();
            System.out.println("begin:" + begin);

            CompletionService<String> completionServiceBase = new ExecutorCompletionService<>(BASE_THREAD_POOL);
            for (int i = 0; i < 5; i++) {
                completionServiceBase.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        int time = new Random().nextInt(10000);
                        System.out.println("执行耗时:" + time);
                        Thread.sleep(time);
                        return Thread.currentThread().getName() + " " + time + " " + System.currentTimeMillis();
                    }
                });
            }
            for (int i = 0; i < 5; i++) {
                //一直等,执行时间最短的线程先返回
                Future<String> baseParamFuture = completionServiceBase.take();

                //有超时时间
                //Future<String> baseParamFuture = completionServiceBase.poll(100,TimeUnit.MILLISECONDS);
                if (baseParamFuture != null) {
                    String oneResult = baseParamFuture.get();
                    System.out.println(oneResult);
                } else {
                    System.out.println("超时:" + System.currentTimeMillis());
                }
            }

            long end = System.currentTimeMillis();
            System.out.println("end:" + end);

            System.out.println("总耗时：" + (end - begin));

            BASE_THREAD_POOL.shutdown();

        } catch (Exception e) {
            LOGGER.error("errorMessage={}", e.getMessage(), e);
        }
    }

}
