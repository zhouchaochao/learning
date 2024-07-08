package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: https://leetcode.cn/problems/remove-element/solutions/575555/shua-chuan-lc-shuang-bai-shuang-zhi-zhen-mzt8/?envType=study-plan-v2&envId=top-interview-150
 * 27. 移除元素
 * @date: 2024-03-26
 */

public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        int idx = 0;
        for (int x : nums) {
            if (x != val) {
                nums[idx++] = x;
            }
        }
        return idx;
    }
}
