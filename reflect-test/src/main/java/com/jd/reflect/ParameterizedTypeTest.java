package com.jd.reflect;

import com.jd.reflect.entity.Apple;
import com.jd.reflect.entity.Father;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Title: ParameterizedTypeTest
 * Description: ParameterizedTypeTest
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/6/12
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class ParameterizedTypeTest {

    private static Logger logger = LoggerFactory.getLogger(ParameterizedTypeTest.class);

    public static class Child extends Father<Apple,String>{

    }

    public static void main(String[] args) {
        try{
            Child c = new Child();

            Random random = new Random(13);
            System.out.println(random.nextInt(100));
            System.out.println(random.nextInt(100));
            System.out.println(random.nextInt(100));

            System.out.println(Math.random());

            /**
             runtime_class:class com.jd.reflect.ParameterizedTypeTest$Child
             getGenericSuperclass type = com.jd.reflect.entity.Father<com.jd.reflect.entity.Apple, java.lang.String>
             trueType1 = class com.jd.reflect.entity.Apple
             trueType2 = class java.lang.String
             entityClass = class com.jd.reflect.entity.Apple
             entityClass2 = class java.lang.String
             A is B's super class :0
             */

        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

}
