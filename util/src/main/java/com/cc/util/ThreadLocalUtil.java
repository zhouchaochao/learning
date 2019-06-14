package com.cc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title: ThreadLocalUtil
 * Description: ThreadLocalUtil
 * Date:  2018/8/15
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ThreadLocalUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalUtil.class);

    private static Map<String,ThreadLocal<Object>> ThreadLocalMap = new ConcurrentHashMap<String,ThreadLocal<Object>>();

    public static Object get(String key){
        try {
            return ThreadLocalMap.get(key).get();
        } catch (Exception e) {
            return null;
        }
    }

    public static void set(String key, Object value) {
        ThreadLocal<Object> threadLocal = ThreadLocalMap.get(key);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<Object>();
            ThreadLocalMap.put(key, threadLocal);
        }
        threadLocal.set(value);
    }

}
