package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 201. 数字范围按位与
 * https://leetcode.cn/problems/bitwise-and-of-numbers-range/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-17
 */

public class BitwiseAndOfNumbersRange {

    public int rangeBitwiseAnd(int m, int n) {
        while (m < n) {
            // 抹去最右边的 1
            n = n & (n - 1);
        }
        return n;
    }
}
