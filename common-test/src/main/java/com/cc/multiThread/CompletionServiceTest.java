package com.cc.multiThread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
            //new CompletionServiceTest().completionServiceTest();
            new CompletionServiceTest().concurrentTest();
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

    public void concurrentTest() {

        ExecutorService COMMON_EXECUTOR_SERVICE = new ThreadPoolExecutor(10, 10,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000000), new ThreadFactoryBuilder().setNameFormat("common-pool-%d").build(), new ThreadPoolExecutor.DiscardPolicy());

        try {
            List<String> configServerIps = new ArrayList<>();
            configServerIps.add("a");
            configServerIps.add("b");

            Map<String, String> transferMap = new ConcurrentHashMap<>(configServerIps.size());
            CompletionService<String> completionServiceBase = new ExecutorCompletionService<>(COMMON_EXECUTOR_SERVICE);
            for (String configServerIp : configServerIps) {
                completionServiceBase.submit(new Callable<String>() {
                    @Override
                    public String call() {
                        String value = getValue(configServerIp);
                        if (true) {
                            //throw new RuntimeException("businessException configserver:" + configServerIp);
                        }

                        transferMap.put(configServerIp, value);
                        return value;
                    }
                });
            }

            for (int i = 0; i < configServerIps.size(); i++) {

                //Future<String> future = completionServiceBase.poll(2, TimeUnit.MILLISECONDS);
                Future<String> future = completionServiceBase.poll(100, TimeUnit.MILLISECONDS);
                if (future != null) {
                    try {
                        //如果有业务异常，get的时候抛出。如果不get,业务异常是不会抛出的
                        future.get();
                    } catch (Exception e) {
                        LOGGER.error("get business execption from call.errorMessage={}",e.getMessage(), e);
                        throw e;
                    }
                } else {
                    LOGGER.warn("业务执行超时");
                    throw new TimeoutException("业务执行超时");
                }
            }

            for (Entry<String, String> entry : transferMap.entrySet()) {
                LOGGER.info("{} -> {}", entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            LOGGER.error("errorMessage={}", e.getMessage(), e);
        }
    }

    private String getValue(String ip) {
        return ip + ip;
    }

}
