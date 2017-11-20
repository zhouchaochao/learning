package com.jd.reflect;

import com.jd.reflect.entity.Person;

import java.lang.reflect.*;

/**
 * Title: Demo
 * Description: Demo
 * 参考：http://www.cnblogs.com/rollenholt/archive/2011/09/02/2163758.html
 *       http://www.cnblogs.com/gulvzhe/archive/2012/01/27/2330001.html
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/15
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class Demo {

    public static void main(String[] args) {

        //testClassName();
        //testNewInstance();
        //testConstructor();
        testGetInterfaces();
        testGetSuperclass();
        testGetConstructors();
        testGetParameterTypes();
        testGetMethods();
        testGetFields();
        testInvoke();
        testGetSet();
        testFieldSet();
        testArray();
        testArrayIncrement();

        testPrintClass("com.jd.reflect.entity.Person");
        getDeclaredField();
        testCopy();
        testCopy2();
    }

    public static void testClassName() {
        Demo demo = new Demo();
        //获得完整的包名和类名
        System.out.println(demo.getClass().getName());//com.jd.reflect.Demo

        Class<?> demo1 = null;
        Class<?> demo2 = null;
        Class<?> demo3 = null;
        try {
            //一般尽量采用这种形式
            demo1 = Class.forName("com.jd.reflect.Demo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        demo2 = new Demo().getClass();
        demo3 = Demo.class;

        System.out.println("类名称   " + demo1.getName());
        System.out.println("类名称   " + demo2.getName());
        System.out.println("类名称   " + demo3.getName());
    }

    //通过Class实例化其他类的对象
    //但是注意一下，当我们把Person中的默认的无参构造函数取消的时候，比如自己定义只定义一个有参数的构造函数之后，会出现错误：java.lang.InstantiationException
    //所以大家以后再编写使用Class实例化其他类的对象的时候，一定要自己定义无参的构造函数
    public static void testNewInstance() {
        Class<?> pClass = null;
        try {
            pClass = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Person per = null;
        try {
            per = (Person) pClass.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        per.setName("Rollen");
        per.setAge(20);
        System.out.println(per);
    }

    public static void testConstructor() {
        Class<?> personClass = null;
        try {
            personClass = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Person per1 = null;
        Person per2 = null;
        Person per3 = null;
        Person per4 = null;
        //取得全部的构造函数
        Constructor<?> cons[] = personClass.getConstructors();
        try {
            //TODO 这个顺序，太奇怪了。反序？？？？
            per1 = (Person) cons[3].newInstance();
            per2 = (Person) cons[2].newInstance("Rollen");
            per3 = (Person) cons[1].newInstance(20);
            per4 = (Person) cons[0].newInstance("Rollen", 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(per1);
        System.out.println(per2);
        System.out.println(per3);
        System.out.println(per4);
    }


    //返回一个类实现的接口
    public static void testGetInterfaces() {
        Class<?> personClass = null;
        try {
            personClass = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //保存所有的接口
        Class<?> intes[] = personClass.getInterfaces();
        for (int i = 0; i < intes.length; i++) {
            System.out.println("实现的接口   " + intes[i].getName());
        }
    }

    //取得父类
    public static void testGetSuperclass() {
        Class<?> personClass = null;
        try {
            personClass = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //取得父类
        Class<?> temp = personClass.getSuperclass();
        System.out.println("继承的父类为：   " + temp.getName());
    }


    //获得全部构造函数
    public static void testGetConstructors() {
        Class<?> personClass = null;
        try {
            personClass = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constructor<?> cons[] = personClass.getConstructors();
        for (int i = 0; i < cons.length; i++) {
            System.out.println("构造方法：  " + cons[i]);
        }
    }


    public static void testGetParameterTypes() {
        Class<?> personClass = null;
        try {
            personClass = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constructor<?> cons[] = personClass.getConstructors();
        for (int i = 0; i < cons.length; i++) {
            Class<?> p[] = cons[i].getParameterTypes();
            System.out.print("testGetParameterTypes 构造方法：  ");
            int mo = cons[i].getModifiers();
            System.out.print(Modifier.toString(mo) + " ");//获取修饰符
            System.out.print(cons[i].getName());
            System.out.print("(");
            for (int j = 0; j < p.length; ++j) {
                System.out.print(p[j].getName() + " arg" + j);
                if (j < p.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("){}");
        }
    }


    public static void testGetMethods() {
        Class<?> demo = null;
        try {
            demo = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Method method[] = demo.getMethods();
        for (int i = 0; i < method.length; ++i) {
            Class<?> returnType = method[i].getReturnType();
            Class<?> para[] = method[i].getParameterTypes();
            int temp = method[i].getModifiers();
            System.out.print(Modifier.toString(temp) + " ");
            System.out.print(returnType.getName() + "  ");
            System.out.print(method[i].getName() + " ");
            System.out.print("(");
            for (int j = 0; j < para.length; ++j) {
                System.out.print(para[j].getName() + " " + "arg" + j);
                if (j < para.length - 1) {
                    System.out.print(",");
                }
            }
            Class<?> exce[] = method[i].getExceptionTypes();
            if (exce.length > 0) {
                System.out.print(") throws ");
                for (int k = 0; k < exce.length; ++k) {
                    System.out.print(exce[k].getName() + " ");
                    if (k < exce.length - 1) {
                        System.out.print(",");
                    }
                }
            } else {
                System.out.print(")");
            }
            System.out.println();
        }
    }

    public static void testGetFields() {
        Class<?> demo = null;
        try {
            demo = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("===============本类属性========================");
        // 取得本类的全部属性
        Field[] field = demo.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            // 权限修饰符
            int mo = field[i].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = field[i].getType();
            System.out.println(priv + " " + type.getName() + " "
                    + field[i].getName() + ";");
        }
        System.out.println("===============实现的接口或者父类的属性========================");
        // 取得实现的接口或者父类的属性
        Field[] filed1 = demo.getFields();
        for (int j = 0; j < filed1.length; j++) {
            // 权限修饰符
            int mo = filed1[j].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = filed1[j].getType();
            System.out.println(priv + " " + type.getName() + " "
                    + filed1[j].getName() + ";");
        }
    }

    //通过反射调用方法
    public static void testInvoke() {
        System.out.println("===============testInvoke========================");
        Class<?> demo = null;
        try {
            demo = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //调用Person类中的hello方法
            Method method = demo.getMethod("hello");
            method.invoke(demo.newInstance());

            //调用Person的say方法
            method = demo.getMethod("eat", String.class);
            method.invoke(demo.newInstance(), "meet");

            //调用Person的say方法
            method = demo.getMethod("say", String.class, int.class);
            method.invoke(demo.newInstance(), "miao", 3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置属性，调用get,set方法
    public static void testGetSet() {
        System.out.println("===============testGetSet========================");
        Class<?> demo = null;
        Object obj = null;
        try {
            demo = Class.forName("com.jd.reflect.entity.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            obj = demo.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Method method = obj.getClass().getMethod("set" + "Name", String.class);//注意name属性首字母大写
            method.invoke(obj, "cc");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Method method = obj.getClass().getMethod("get" + "Name");
            System.out.println(method.invoke(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //通过field 反射操作属性
    public static void testFieldSet() {
        System.out.println("===============testFieldSet========================");
        try {
            Class<?> demo = Class.forName("com.jd.reflect.entity.Person");
            Object obj = demo.newInstance();

            Field field = demo.getDeclaredField("name");
            field.setAccessible(true);
            field.set(obj, "zcc");//设置属性

            System.out.println(field.get(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //通过反射取得并修改数组的信息 TODO 这是反射？
    public static void testArray() {
        System.out.println("===============testArray========================");

        int[] temp = {1, 2, 3, 4, 5};
        Class<?> demo = temp.getClass().getComponentType();
        System.out.println("数组类型： " + demo.getName());
        System.out.println("数组长度  " + Array.getLength(temp));
        System.out.println("数组的第一个元素: " + Array.get(temp, 0));
        Array.set(temp, 0, 100);
        System.out.println("修改之后数组第一个元素为：" + Array.get(temp, 0));
    }

    //通过反射修改数组大小
    public static void testArrayIncrement() {
        System.out.println("===============testArrayIncrement========================");
        int[] temp = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] newTemp = (int[]) arrayInc(temp, 15);
        print(newTemp);
        System.out.println("=====================");
        String[] atr = {"a", "b", "c"};
        String[] str1 = (String[]) arrayInc(atr, 8);
        print(str1);
        System.out.println("");
    }

    /**
     * 修改数组大小
     */
    public static Object arrayInc(Object obj, int len) {
        System.out.println("修改数组大小");
        Class<?> arr = obj.getClass().getComponentType();
        Object newArr = Array.newInstance(arr, len);//创建一个新数组
        int co = Array.getLength(obj);
        System.arraycopy(obj, 0, newArr, 0, co);
        return newArr;
    }

    /**
     * 打印
     */
    public static void print(Object obj) {
        Class<?> c = obj.getClass();
        if (!c.isArray()) {
            return;
        }
        System.out.println("数组长度为： " + Array.getLength(obj));
        for (int i = 0; i < Array.getLength(obj); i++) {
            System.out.print(Array.get(obj, i) + " ");
        }
    }

    public static void testPrintClass(String className) {
        System.out.println("===============testPrintClass========================");

        try {
            //获取整个类
            Class c = Class.forName(className);
            //获取所有的属性
            Field[] fs = c.getDeclaredFields();

            //定义可变长的字符串，用来存储属性
            StringBuffer sb = new StringBuffer();
            //通过追加的方法，将每个属性拼接到此字符串中
            //最外边的public定义
            sb.append(Modifier.toString(c.getModifiers()) + " class " + c.getSimpleName() + "{\n");
            //里边的每一个属性
            for (Field field : fs) {
                sb.append("\t");//空格
                sb.append(Modifier.toString(field.getModifiers()) + " ");//获得属性的修饰符，例如public，static等等
                sb.append(field.getType().getSimpleName() + " ");//属性的类型的名字
                sb.append(field.getName() + ";\n");//属性的名字+回车
            }

            sb.append("}");

            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取特定的属性
    public static void getDeclaredField() {
        System.out.println("===============getDeclaredField========================");

        try {
            //获取类
            Class c = Class.forName("com.jd.reflect.entity.Person");
            //获取name属性
            Field idF = c.getDeclaredField("name");
            //实例化这个类赋给o
            Object o = c.newInstance();
            //打破封装
            idF.setAccessible(true); //使用反射机制可以打破封装性，导致了java对象的属性不安全。
            //给o对象的id属性赋值"110"
            idF.set(o, "cc"); //set
            //get
            System.out.println(idF.get(o));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testCopy() {
        System.out.println("===============testCopy========================");

        try {

            Person person1 = new Person("cc", 18);
            Person person2 = new Person();

            copyBean(person1, person2);

            System.out.println("源对象：" + person1);
            System.out.println("拷贝对象：" + person2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 拷贝对象信息。
     *
     * @param from 拷贝源对象
     * @param dest 拷贝目标对象
     * @throws Exception 例外
     */
    private static void copyBean(Object from, Object dest) throws Exception {
        System.out.println("copyBean");
        // 取得拷贝源对象的Class对象
        Class<?> fromClass = from.getClass();
        // 取得拷贝源对象的属性列表
        Field[] fromFields = fromClass.getDeclaredFields();
        // 取得拷贝目标对象的Class对象
        Class<?> destClass = dest.getClass();
        Field destField = null;
        for (Field fromField : fromFields) {
            // 取得拷贝源对象的属性名字
            String name = fromField.getName();
            // 取得拷贝目标对象的相同名称的属性
            destField = destClass.getDeclaredField(name);
            // 设置属性的可访问性
            fromField.setAccessible(true);
            destField.setAccessible(true);
            // 将拷贝源对象的属性的值赋给拷贝目标对象相应的属性
            destField.set(dest, fromField.get(from));
        }
    }


    public static void testCopy2() {
        try {
            System.out.println("===============testCopy2========================");

            Person person1 = new Person("ccc", 19);
            Person person2 = (Person)copyBean(person1);

            System.out.println("源对象：" + person1);
            System.out.println("拷贝对象：" + person2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object copyBean(Object from) throws Exception {
        System.out.println("===============copyBean========================");
        // 取得拷贝源对象的Class对象
        Class<?> fromClass = from.getClass();
        // 取得拷贝源对象的属性列表
        Field[] fromFields = fromClass.getDeclaredFields();
        // 取得拷贝目标对象的Class对象
        Object ints = fromClass.newInstance();
        for (Field fromField : fromFields) {
            // 设置属性的可访问性
            fromField.setAccessible(true);
            // 将拷贝源对象的属性的值赋给拷贝目标对象相应的属性
            fromField.set(ints, fromField.get(from));
        }
        return ints;
    }

}