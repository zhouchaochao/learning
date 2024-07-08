package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 190. 颠倒二进制位
 * https://leetcode.cn/problems/reverse-bits/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-17
 */

public class ReverseBits {

    public int reverseBits(int n) {
        int rev = 0;
        for (int i = 0; i < 32 && n != 0; ++i) {
            // 只取最后一位，移动到指定位置，加到rev中
            rev |= (n & 1) << (31 - i);
            // 去掉最后一位
            n = n >> 1;
        }
        return rev;
    }
}
