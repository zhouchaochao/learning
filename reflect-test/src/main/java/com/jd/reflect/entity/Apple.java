package com.jd.reflect.entity;

import com.jd.reflect.iface.Fruit;

/**
 * Title: Apple
 * Description: Apple
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class Apple implements Fruit {
    @Override
    public void eat() {
        System.out.println("Apple eat");
    }
}
