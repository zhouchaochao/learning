package com.jd.reflect;

import com.jd.reflect.iface.Fruit;

/**
 * Title: FruitFactory
 * Description: FruitFactory
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class FruitFactory {

    public static Fruit getInstance(String ClassName) {
        Fruit f = null;
        try {
            f = (Fruit) Class.forName(ClassName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

}
