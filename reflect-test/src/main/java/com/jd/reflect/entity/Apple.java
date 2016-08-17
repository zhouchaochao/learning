package com.jd.reflect.entity;

import com.jd.reflect.iface.Fruit;

/**
 * Title: Apple
 * Description: Apple
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class Apple implements Fruit {
    @Override
    public void eat() {
        System.out.println("Apple eat");
    }
}
