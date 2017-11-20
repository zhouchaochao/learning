package com.jd;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Title: MainArguments
 * Description: MainArguments
 *              path:../java/com/jd/MainArguments.java
 *              in java path:1.javac com/jd/MainArguments.java
 *                           2.java com.jd.MainArguments a b c
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/11/4
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class MainArguments {

    public static void main(String[] args){
        System.out.println(args.length);
        System.out.println(Arrays.toString(args));

        Scanner scanner = new Scanner(System.in);
        String a1 = scanner.nextLine();
        String a2 = scanner.nextLine();
        System.out.println("a1:" + a1);
        System.out.println("a2:" + a2);
    }
}
