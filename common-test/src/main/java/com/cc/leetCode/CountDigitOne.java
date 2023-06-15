package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 233. 数字 1 的个数
 * https://leetcode.cn/problems/number-of-digit-one/
 * 给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
 * 提示：0 <= n <= 109
 * @date: 2023-06-14
 */

public class CountDigitOne {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountDigitOne.class);

    public static void main(String[] args) {

        int n = 0;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 1;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 9;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 10;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 11;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 13;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 113;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 123;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));

        n = 213;
        System.out.println(n + " -> " + new CountDigitOne().countDigitOne(n));
    }

    public int countDigitOne(int n) {
        if (n == 0) {
            return 0;
        }

        String valueStr = String.valueOf(n);
        int length = valueStr.length();
        int count = 0;
        for (int i = 0; i < length; i++) {
            // 单独计算每一个index位置存在1的可能数量
            count += oneNumOfIndexI(n, i, valueStr.charAt(i), length);
            //System.out.println("index:" + i + " afterCount:" + count);
        }

        return count;
    }

    /**
     * 说明：返回n中index位置存在1的可能数量
     *
     * 举例：n=123456 index=2 indexValue='3' length=6
     * 如果i在最高位(0)
     * 情况1 data[i]=1 （右侧值 + 1）
     * 情况2 data[i]>1  右侧位全排
     * 否则
     * 情况1 data[i]=0 考虑将i位置减到9，结果不变，结果：左侧值 * 右侧位全排
     * 情况2 data[i]=1 考虑将i位置减到9，结果：左侧值 * 右侧位全排 +（右侧值 + 1）
     * 情况3 data[i]>1 直接计算，（左侧值+1）* 右侧位全排
     *
     * @param n
     * @param index
     * @param indexValue
     * @param length
     * @return
     */
    private int oneNumOfIndexI(int n, int index, char indexValue, int length) {
        if (index == 0) {
            if ('1' == indexValue) {
                return n - (int) Math.pow(10, length - 1) + 1;
            } else {
                return (int) Math.pow(10, length - 1);
            }
        } else {
            if ('0' == indexValue) {
                return (n / (int) Math.pow(10, length - index)) * (int) Math.pow(10, length - index - 1);
            } else if ('1' == indexValue) {
                return (n / (int) Math.pow(10, length - index)) * (int) Math.pow(10, length - index - 1) + ((n % (int) Math.pow(10, length - index - 1)) + 1);
            } else {
                return (n / (int) Math.pow(10, length - index) + 1) * (int) Math.pow(10, length - index - 1);
            }
        }
    }
}
