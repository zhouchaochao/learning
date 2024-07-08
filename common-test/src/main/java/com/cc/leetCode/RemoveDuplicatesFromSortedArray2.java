package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc:  https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/description/?envType=study-plan-v2&envId=top-interview-150
 * 80. 删除有序数组中的重复项 II
 * @date: 2024-03-26
 */

public class RemoveDuplicatesFromSortedArray2 {

    public int removeDuplicates(int[] nums) {
        return process(nums, 2);
    }
    int process(int[] nums, int k) {
        int u = 0;
        for (int x : nums) {
            if (u < k || nums[u - k] != x) {
                nums[u++] = x;
            }
        }
        return u;
    }
}
