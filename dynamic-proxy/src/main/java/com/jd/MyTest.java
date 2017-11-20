package com.jd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Title: MyTest
 * Description: MyTest
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class MyTest {

    public static void main(String[] args) throws IOException {

        try{
            //读取本地的class文件内的字节码，转换成字节码数组
/*            File file = new File(".");
            System.out.println(file.getCanonicalPath());*/

            //InputStream input = new FileInputStream(file.getCanonicalPath()+"\\bin\\samples\\Programmer.class");
            InputStream input = new FileInputStream("D:\\temp\\com\\samples\\Programmer.class");

            byte[] result = new byte[1024];
            int count = input.read(result);
            // 使用自定义的类加载器将 byte字节码数组转换为对应的class对象
            MyClassLoader loader = new MyClassLoader();
            Class clazz = loader.defineMyClass( result, 0, count);
            //测试加载是否成功，打印class 对象的名称
            System.out.println(clazz.getCanonicalName());

            //实例化一个Programmer对象
            Object o= clazz.newInstance();

            //调用Programmer的code方法
            clazz.getMethod("code", null).invoke(o, null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
