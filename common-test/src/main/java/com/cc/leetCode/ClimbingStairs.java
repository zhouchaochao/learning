package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 70. 爬楼梯
 * https://leetcode.cn/problems/climbing-stairs/description/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-19
 */

public class ClimbingStairs {

    public int climbStairs(int n) {
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; ++i) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }
}
