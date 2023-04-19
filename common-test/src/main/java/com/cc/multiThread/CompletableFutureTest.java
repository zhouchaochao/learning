package com.cc.multiThread;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: CompletableFutureTest
 * Date:  2021/6/18
 *
 * @author <a href=mailto:qiao.zzc@autonavi.com>yuhu</a>
 */

public class CompletableFutureTest {

    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureTest.class);

    private Executor executor = new ThreadPoolExecutor(240, 10000, 500, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build());

    private static final ThreadPoolExecutor DIFF_THREAD_POOL = new ThreadPoolExecutor(8, 16, 500, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), new ThreadFactoryBuilder().setNameFormat("diff-thread-pool-%d").build(), new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        new CompletableFutureTest().test();
        System.exit(0);
    }

    public void test() {
        try {

            Map<Integer, CompletableFuture<String>> completableFutureMap = new HashMap<>();

            for (int i = 0; i < 10; i++) {
                final Integer param = i;
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {

                    if (param == 2) {
                        try {
                            Thread.sleep(1001);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (param == 3) {
                        throw new IllegalArgumentException("IllegalArgumentException");
                    }

                    if (param == 4) {
                        throw new RuntimeException("RuntimeException");
                    }

                    if (param == 5) {
                        return null;
                    }

                    String result = "" + param;
                    return result;
                }, executor);
                completableFutureMap.put(i, completableFuture);
            }

            for (Entry<Integer, CompletableFuture<String>> integerCompletableFutureEntry : completableFutureMap
                .entrySet()) {
                try {
                    String value = integerCompletableFutureEntry.getValue().get(1000, TimeUnit.MILLISECONDS);
                    System.out.println(integerCompletableFutureEntry.getKey() + " " + value);
                } catch (TimeoutException e) {
                    System.out.println(integerCompletableFutureEntry.getKey() + " " + "执行超时 throw TimeoutException");
                } catch (ExecutionException e) {
                    System.out.println(integerCompletableFutureEntry.getKey() + " " + "执行抛异常 throw ExecutionException");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            logger.error("errorMessage={}", e.getMessage(), e);
        }
    }
}
