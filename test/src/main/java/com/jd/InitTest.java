package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Title: InitTest
 * Description: InitTest
 *  https://www.cnblogs.com/mengdd/p/3562003.html
 * Date:  2019/4/29
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class InitTest {

    private static final Logger logger = LoggerFactory.getLogger(InitTest.class);

    public static void main(String[] args) {
        System.out.println("FinalTest1: " + FinalTest1.x);
        //System.out.println("FinalTest1: " + new FinalTest1().x);
        System.out.println("FinalTest2: " + FinalTest2.x);
    }
}

class FinalTest1 {
    public static final int x = 6 / 3; // 编译时期已经可知其值为2，是常量
    // 类型不需要进行初始化
    static {
        System.out.println("static block in FinalTest1");
        // 此段语句不会被执行，即无输出
    }
}

class FinalTest2 {
    public static final int x = new Random().nextInt(100);// 只有运行时才能得到值
    static {
        System.out.println("static block in FinalTest2");
        // 会进行类的初始化，即静态语句块会执行，有输出
    }
}
