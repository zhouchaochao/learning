package com.jd.reflect;

import com.jd.reflect.iface.Subject;

/**
 * Title: RealSubject
 * Description: RealSubject
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/16
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class RealSubject implements Subject {

    @Override
    public String say(String name, int age) {
        return name + "  " + age;
    }

}
