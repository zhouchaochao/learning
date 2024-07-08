package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 191. 位1的个数
 * https://leetcode.cn/problems/number-of-1-bits/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-17
 */

public class NumberOf1Bits {

    public int hammingWeight(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) != 0) {
                ret++;
            }
        }
        return ret;
    }

    public int hammingWeight2(int n) {
        int ret = 0;
        while (n != 0) {
            n &= n - 1;
            ret++;
        }
        return ret;
    }
}
