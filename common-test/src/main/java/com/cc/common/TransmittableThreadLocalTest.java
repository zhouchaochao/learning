package com.cc.common;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: ThreadLocalTest
 * @date: 2/10/22
 */

public class TransmittableThreadLocalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransmittableThreadLocalTest.class);

    // 1. 初始化一个TransmittableThreadLocal，这个是继承了InheritableThreadLocal的
    static TransmittableThreadLocal<HintContent> local = new TransmittableThreadLocal<>();

    // 初始化一个长度为1的线程池
    static ExecutorService poolExecutor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new TransmittableThreadLocalTest().test();
    }

    private void test() throws ExecutionException, InterruptedException {
        // 设置初始值
        local.set(new HintContent("新年快乐", 1));
        System.out.println(Thread.currentThread().getName() + "父线程设置："+local.get());
        //！！！！ 注意：这个地方的Task是使用了TtlRunnable包装的
        //Future future = poolExecutor.submit(TtlRunnable.get(new Task("任务1")));
        Future future = poolExecutor.submit(new Task("任务1"));
        //future.get();

        //Future future2 = poolExecutor.submit(TtlRunnable.get(new Task("任务2")));
        Future future2 = poolExecutor.submit(new Task("任务2"));
        //future2.get();

        try {
            //local.get().setName("happy new year");
            local.set(new HintContent("happy new year", 2));
            System.out.println(Thread.currentThread().getName() + "父线程重新设置："+local.get());
            Thread.sleep(3000);
        } catch (Exception e) {
            LOGGER.error("errorMessage={}", e.getMessage(), e);
        }

        System.out.println(Thread.currentThread().getName() + "父线程获取值："+local.get());
        poolExecutor.shutdown();
    }

    class Task implements Runnable{

        String str;
        Task(String str){
            this.str = str;
        }
        @Override
        public void run() {

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                LOGGER.error("errorMessage={}", e.getMessage(), e);
            }

            // 获取值
            System.out.println(Thread.currentThread().getName()+"获取到:"+local.get());
            // 重新设置一波
            //local.set(str);
            //System.out.println(Thread.currentThread().getName() + "重新设置为：" + local.get());
        }
    }

    class HintContent {
        String name = "";
        Integer index  = 0;

        public HintContent(String name,Integer index){
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name + index;
        }
    }
}
