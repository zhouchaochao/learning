package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 167. 两数之和 II - 输入有序数组
 * https://leetcode.cn/problems/two-sum-ii-input-array-is-sorted/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-01
 */

public class TwoSum2InputArrayIsSorted {

    public int[] twoSum(int[] numbers, int target) {
        int low = 0, high = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];
            if (sum == target) {
                return new int[]{low + 1, high + 1};
            } else if (sum < target) {
                ++low;
            } else {
                --high;
            }
        }
        return new int[]{-1, -1};
    }

}
