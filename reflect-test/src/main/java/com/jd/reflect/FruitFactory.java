package com.jd.reflect;

import com.jd.reflect.iface.Fruit;

/**
 * Title: FruitFactory
 * Description: FruitFactory
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
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
