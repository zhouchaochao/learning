package com.jd.reflect.entity;

import com.jd.reflect.iface.Fruit;

/**
 * Title: Orange
 * Description: Orange
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class Orange implements Fruit {
    @Override
    public void eat() {
        System.out.println("Orange eat");
    }
}
