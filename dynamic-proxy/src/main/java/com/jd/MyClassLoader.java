package com.jd;

/**
 * Title: MyClassLoader
 * Description: MyClassLoader
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class MyClassLoader extends ClassLoader {

    public Class<?> defineMyClass( byte[] b, int off, int len)
    {
        return super.defineClass(null,b, off, len);
    }

}