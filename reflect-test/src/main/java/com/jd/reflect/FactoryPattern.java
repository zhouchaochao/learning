package com.jd.reflect;

import com.jd.reflect.iface.Fruit;

/**
 * Title: FactoryPattern 通过反射的工厂模式
 * Description: FactoryPattern
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class FactoryPattern {

    public static void main(String[] agrs){

        Fruit f=FruitFactory.getInstance("com.jd.reflect.entity.Apple");
        if(f!=null){
            f.eat();
        }

        f=FruitFactory.getInstance("com.jd.reflect.entity.Orange");
        if(f!=null){
            f.eat();
        }
    }

}
