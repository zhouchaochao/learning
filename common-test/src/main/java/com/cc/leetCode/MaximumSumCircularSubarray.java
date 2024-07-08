package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 918. 环形子数组的最大和
 * https://leetcode.cn/problems/maximum-sum-circular-subarray/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-12
 */

public class MaximumSumCircularSubarray {


    public int maxSubarraySumCircular(int[] nums) {
        int n = nums.length;
        // dp, leftMax[i] 表示[0,i]这一区间中从0开始的前缀和的最大值（也就是Max( sum([0 -- j]) ), j<=i）
        int[] leftMax = new int[n];
        // 对坐标为 0 处的元素单独处理，避免考虑子数组为空的情况
        leftMax[0] = nums[0];
        int leftSum = nums[0];
        int pre = nums[0];
        int res = nums[0];
        for (int i = 1; i < n; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            res = Math.max(res, pre);
            leftSum += nums[i];
            leftMax[i] = Math.max(leftMax[i - 1], leftSum);
        }

        // 从右到左枚举后缀，固定后缀，选择最大前缀
        int rightSum = 0;
        for (int i = n - 1; i > 0; i--) {
            rightSum += nums[i];
            res = Math.max(res, rightSum + leftMax[i - 1]);
        }
        return res;
    }


    public int maxSubarraySumCircular2(int[] nums) {
        int n = nums.length;
        int preMax = nums[0], maxRes = nums[0];
        int preMin = nums[0], minRes = nums[0];
        int sum = nums[0];
        for (int i = 1; i < n; i++) {
            preMax = Math.max(preMax + nums[i], nums[i]);
            maxRes = Math.max(maxRes, preMax);
            preMin = Math.min(preMin + nums[i], nums[i]);
            minRes = Math.min(minRes, preMin);
            sum += nums[i];
        }
        if (maxRes < 0) {
            return maxRes;
        } else {
            return Math.max(maxRes, sum - minRes);
        }
    }

}
