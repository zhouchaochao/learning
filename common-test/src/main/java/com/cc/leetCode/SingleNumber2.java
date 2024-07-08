package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 137. 只出现一次的数字 II
 * https://leetcode.cn/problems/single-number-ii/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-17
 */

public class SingleNumber2 {

    public int singleNumber(int[] nums) {
        int ans = 0;
        for (int i = 0; i < 32; ++i) {
            int total = 0;
            for (int num: nums) {
                total += ((num >> i) & 1);
            }
            if (total % 3 != 0) {
                ans |= (1 << i);
            }
        }
        return ans;
    }

}
