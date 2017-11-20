package com.jd.reflect;

import com.jd.reflect.iface.Subject;

/**
 * Title: RealSubject
 * Description: RealSubject
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/16
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class RealSubject implements Subject {

    @Override
    public String say(String name, int age) {
        String returnStr = name + "  " + age;
        System.out.println(returnStr);
        return returnStr;
    }

}
