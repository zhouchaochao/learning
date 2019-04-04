package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: NumberOfDigitOne
 * Description: NumberOfDigitOne 给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
 * 233. 数字1的个数
 * https://leetcode-cn.com/problems/number-of-digit-one/
 * Date:  2019/4/3
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class NumberOfDigitOne {

    private static final Logger logger = LoggerFactory.getLogger(NumberOfDigitOne.class);

    //给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
    public static void main(String[] args) {
/*        System.out.println(new NumberOfDigitOne().countDigitOne(0));
        System.out.println(new NumberOfDigitOne().countDigitOne(1));
        System.out.println(new NumberOfDigitOne().countDigitOne(3));
        System.out.println(new NumberOfDigitOne().countDigitOne(10));
        System.out.println(new NumberOfDigitOne().countDigitOne(11));
        System.out.println(new NumberOfDigitOne().countDigitOne(12));
        System.out.println(new NumberOfDigitOne().countDigitOne(13));
        System.out.println(new NumberOfDigitOne().countDigitOne(19));
        System.out.println(new NumberOfDigitOne().countDigitOne(20));
        System.out.println(new NumberOfDigitOne().countDigitOne(23));
        System.out.println(new NumberOfDigitOne().countDigitOne(100));
        System.out.println(new NumberOfDigitOne().countDigitOne(101));*/
        System.out.println(new NumberOfDigitOne().countDigitOne(8192));//3533
    }

    public int countDigitOne(int n) {
        if (n < 10) {
            return n >= 1 ? 1 : 0;
        }
        int sum = 0;
        String numStr = "" + n;
        for (int i = 0; i < numStr.length(); i++) {
            sum += countDigitOne(numStr,i);
        }
        return sum;
    }

    public int countDigitOne(String numStr,int index) {
        int result = 0;
        if (index == 0) {
            String rightSide = numStr.substring(index + 1);
            if (numStr.substring(index, index + 1).equals("1")) {
                return Integer.valueOf(rightSide) + 1;
            } else {
                return (int) Math.pow(10, rightSide.length());
            }
        }
        if (index == numStr.length()-1) {
            String leftSide = numStr.substring(0, index);
            if (numStr.substring(index, index + 1).equals("0")) {
                return Integer.valueOf(leftSide);
            } else {
                return Integer.valueOf(leftSide) + 1;
            }
        }

        String leftSide = numStr.substring(0, index);
        String rightSide = numStr.substring(index + 1);
        if (numStr.substring(index, index + 1).equals("0")) {
            result =  Integer.valueOf(leftSide) * (int) Math.pow(10, rightSide.length());
        } else if (numStr.substring(index, index + 1).equals("1")) {
            result = Integer.valueOf(leftSide) * (int) Math.pow(10, rightSide.length()) + Integer.valueOf(rightSide) + 1 ;
        } else {
            result = Integer.valueOf(leftSide) * (int) Math.pow(10, rightSide.length()) + (int) Math.pow(10, rightSide.length());
        }
        //System.out.println("index" + index + " :" + result);
        return result;
    }
}
