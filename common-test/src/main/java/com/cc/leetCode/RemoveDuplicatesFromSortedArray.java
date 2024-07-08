package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/?envType=study-plan-v2&envId=top-interview-150
 * 26. 删除有序数组中的重复项
 * @date: 2024-03-26
 */

public class RemoveDuplicatesFromSortedArray {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveDuplicatesFromSortedArray.class);

    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int p = 0;
        int q = 1;
        while (q < nums.length) {
            if (nums[p] != nums[q]) {
                nums[p + 1] = nums[q];
                p++;
            }
            q++;
        }
        return p + 1;
    }
}
