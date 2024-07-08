package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 172. 阶乘后的零
 * https://leetcode.cn/problems/factorial-trailing-zeroes/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-18
 */

public class FactorialTrailingZeroes {

    public int trailingZeroes(int n) {
        int ans = 0;
        while (n != 0) {
            n /= 5;
            ans += n;
        }
        return ans;
    }
}
