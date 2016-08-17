package com.jd.reflect;

import com.jd.reflect.iface.Subject;

/**
 * Title: DynamicProxy
 * Description: DynamicProxy
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/15
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class DynamicProxy {

    /**
        其实在java中有三种类类加载器:
            1）Bootstrap ClassLoader 此加载器采用c++编写，一般开发中很少见。
            2）Extension ClassLoader 用来进行扩展类的加载，一般对应的是jre\lib\ext目录中的类
            3）AppClassLoader 加载classpath指定的类，是最常用的加载器。同时也是java中默认的加载器。
     */

    public static void main(String[] args) {

        DynamicProxy dynamicProxy = new DynamicProxy();
        System.out.println("类加载器  " + dynamicProxy.getClass().getClassLoader().getClass().getName());//sun.misc.Launcher$AppClassLoader


        MyInvocationHandler demo = new MyInvocationHandler();
        Subject sub = (Subject) demo.bind(new RealSubject());//马蛋，我都能new出实例了，还要你的代理什么用??
        String info = sub.say("Rollen", 20);
        System.out.println(info);

    }

}
