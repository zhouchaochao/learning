package com.jd.reflect.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Title: Father
 * Description: Father
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/6/12
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class Father<T1, T2> {

    private static Logger logger = LoggerFactory.getLogger(Father.class);

    class A {}
    class B extends A {}

    private Class<T1> entityClass;
    private Class<T2> entityClass2;
    public Father(){
        Class runtime_class = getClass();
        Type type = runtime_class.getGenericSuperclass();
        System.out.println("runtime_class:" + runtime_class);
        System.out.println("getGenericSuperclass type = " + type);

        /**
         * ((ParameterizedType)type).getActualTypeArguments() 是用来得到类的模板参数的类型的？ 如T1, T2  etc...
         * 返回表示此类型实际类型参数的 Type 对象的数组。
         */
        Type trueType = ((ParameterizedType)type).getActualTypeArguments()[0];
        System.out.println("trueType1 = " + trueType);
        Type trueType2 = ((ParameterizedType)type).getActualTypeArguments()[1];
        System.out.println("trueType2 = " + trueType2);
        this.entityClass = (Class<T1>)trueType;
        this.entityClass2 = (Class<T2>)trueType2;
        System.out.println("entityClass = " + entityClass);
        System.out.println("entityClass2 = " + entityClass2);

        B t = new B();
        type = t.getClass().getGenericSuperclass();
        System.out.println("A is B's super class :" + ((ParameterizedType)type).getActualTypeArguments().length);
    }

    /**
     runtime_class:class com.jd.reflect.ParameterizedTypeTest$Child
     getGenericSuperclass type = com.jd.reflect.entity.Father<com.jd.reflect.entity.Apple, java.lang.String>
     trueType1 = class com.jd.reflect.entity.Apple
     trueType2 = class java.lang.String
     entityClass = class com.jd.reflect.entity.Apple
     entityClass2 = class java.lang.String
     A is B's super class :0
     */
}
