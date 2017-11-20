package com.jd;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * Title: MyGenerator  http://blog.csdn.net/sinat_18882775/article/details/51061946
 * Description: MyGenerator http://www.blogjava.net/hello-yun/archive/2014/09/28/418365.html
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class MyGenerator {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        //创建Programmer类
        CtClass cc= pool.makeClass("com.samples.Programmer");
        //定义code方法
        CtMethod method = CtNewMethod.make("public void code(){}", cc);
        //插入方法代码
        method.insertBefore("System.out.println(\"I'm a Programmer,Just Coding.....\");");
        cc.addMethod(method);
        //保存生成的字节码
        cc.writeFile("d://temp");
    }
}
