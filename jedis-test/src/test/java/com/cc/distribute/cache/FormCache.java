package com.cc.distribute.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: FormCache
 * Description: FormCache
 * Date:  2018/8/30
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class FormCache {

    private static final Logger logger = LoggerFactory.getLogger(FormCache.class);

    public static Map<Long,String> cache = new HashMap<>();

    public void setInvalidate(String id) {
        try {
            System.out.println("setInvalidate方法执行" + id);
            cache.remove(id);
        } catch (Exception e) {
        }
    }

    public void setInvalidate(String id, Long d) {
        try {
            String className = this.getClass().getName();
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            System.out.println(InvokeUtil.createInvokeEntityJson(className, methodName, id, d));
            System.out.println("setInvalidate方法执行" + id + " d:" + d);
            cache.remove(id);
        } catch (Exception e) {
        }
    }


}
