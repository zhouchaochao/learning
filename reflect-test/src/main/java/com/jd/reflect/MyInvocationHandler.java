package com.jd.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Title: MyInvocationHandler
 * Description: MyInvocationHandler
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/16
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class MyInvocationHandler implements InvocationHandler {
    private Object obj = null;

    public Object bind(Object obj) {
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
                .getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("invoke before");
        Object temp = method.invoke(this.obj, args);
        System.out.println("invoke after");
        return temp;
    }
}
