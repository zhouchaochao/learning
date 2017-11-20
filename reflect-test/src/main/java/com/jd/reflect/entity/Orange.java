package com.jd.reflect.entity;

import com.jd.reflect.iface.Fruit;

/**
 * Title: Orange
 * Description: Orange
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class Orange implements Fruit {
    @Override
    public void eat() {
        System.out.println("Orange eat");
    }
}
