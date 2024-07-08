package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 136. 只出现一次的数字
 * https://leetcode.cn/problems/single-number/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-17
 */

public class SingleNumber {

    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }
}
