package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 153. 寻找旋转排序数组中的最小值
 * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-15
 */

public class FindMinimumInRotatedSortedArray {

    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (nums[pivot] < nums[high]) {
                high = pivot;
            } else {
                low = pivot + 1;
            }
        }
        return nums[low];
    }
}
