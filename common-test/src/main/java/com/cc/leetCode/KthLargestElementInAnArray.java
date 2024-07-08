package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 215. 数组中的第K个最大元素
 * https://leetcode.cn/problems/kth-largest-element-in-an-array/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-16
 */

public class KthLargestElementInAnArray {

    int quickselect(int[] nums, int l, int r, int k) {
        if (l == r) {return nums[k];}
        int x = nums[l], i = l - 1, j = r + 1;
        while (i < j) {
            do {i++;} while (nums[i] < x);
            do {j--;}while (nums[j] > x);
            if (i < j){
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        if (k <= j) {return quickselect(nums, l, j, k);}
        else {return quickselect(nums, j + 1, r, k);}
    }
    public int findKthLargest(int[] _nums, int k) {
        int n = _nums.length;
        return quickselect(_nums, 0, n - 1, n - k);
    }

}
