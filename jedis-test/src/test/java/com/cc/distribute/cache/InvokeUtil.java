package com.cc.distribute.cache;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Title: InvokeUtil
 * Description: InvokeUtil
 * Date:  2018/8/30
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class InvokeUtil {

    private static final Logger logger = LoggerFactory.getLogger(InvokeUtil.class);

    public static String createInvokeEntityJson(String className, String methodName, Object... parms) {

        InvokeEntity invokeEntity = new InvokeEntity();
        invokeEntity.setClassName(className);
        invokeEntity.setMethodName(methodName);
        for (Object parm : parms) {
            invokeEntity.addParam(parm);
        }
        return JSON.toJSONString(invokeEntity);
    }


    @Test
    public void executeInvokeEntity(String invokeEntityJson) throws Exception {

        try {
            InvokeEntity invokeEntity = JSON.parseObject(invokeEntityJson, InvokeEntity.class);
            Class<?> clazz = Class.forName(invokeEntity.getClassName());
            Class[] parasClassArr = new Class[invokeEntity.getParamsType().size()];
            invokeEntity.getParamsType().toArray(parasClassArr);
            Object[] parasArr = invokeEntity.getParamsValue().toArray();
            if (parasClassArr.length == parasArr.length) {
                for (int i = 0; i < parasClassArr.length; i++) {
                    if (parasClassArr[i].equals(String.class)) {
                        parasArr[i] = String.valueOf(parasArr[i]);
                    } else if (parasClassArr[i].equals(Long.class)) {
                        parasArr[i] = Long.valueOf(parasArr[i].toString());
                    } else if (parasClassArr[i].equals(Double.class)) {
                        parasArr[i] = Double.valueOf(parasArr[i].toString());
                    } else if (parasClassArr[i].equals(Integer.class)) {
                        parasArr[i] = Integer.valueOf(parasArr[i].toString());
                    } else if (parasClassArr[i].equals(Short.class)) {
                        parasArr[i] = Short.valueOf(parasArr[i].toString());
                    } else if (parasClassArr[i].equals(Boolean.class)) {
                        parasArr[i] = Boolean.valueOf(parasArr[i].toString());
                    }
                }
            }
            Method method = clazz.getMethod(invokeEntity.getMethodName(), parasClassArr);
            method.invoke(clazz.newInstance(), parasArr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
