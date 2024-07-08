package com.cc.leetCode;


/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: MaximumSubarray
 *  https://leetcode.cn/problems/maximum-subarray/
 *  53. 最大子数组和
 * @date: 2023-08-23
 */

public class MaximumSubarray {

    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(new MaximumSubarray().maxSubArray(nums));
        nums = new int[]{1};
        System.out.println(new MaximumSubarray().maxSubArray(nums));
        nums = new int[]{5, 4, -1, 7, 8};
        System.out.println(new MaximumSubarray().maxSubArray(nums));
        nums = new int[]{-10, 100, -10, 1, -10, 8};
        System.out.println(new MaximumSubarray().maxSubArray(nums));
        nums = new int[]{-10, 100, -10, 1, -10, 100};
        System.out.println(new MaximumSubarray().maxSubArray(nums));
    }

    // 精妙啊，dp
    // 1ms 击败 100.00%使用 Java 的用户
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int leftSum = nums[0];//左侧到当前位置结尾的连续子序列的和
        for (int i = 1; i < nums.length; i++) {
            if (leftSum <=0 ) {
                //左侧和是负债，已经没有意义了，可以另起炉灶
                leftSum = nums[i];
            } else {
                //加入左侧和
                leftSum += nums[i];
            }
            max = max > leftSum ? max : leftSum;
        }
        return max;
    }


    public class Status {
        public int lSum, rSum, mSum, iSum;

        public Status(int lSum, int rSum, int mSum, int iSum) {
            this.lSum = lSum;
            this.rSum = rSum;
            this.mSum = mSum;
            this.iSum = iSum;
        }
    }

    public int maxSubArray2(int[] nums) {
        return getInfo(nums, 0, nums.length - 1).mSum;
    }

    public Status getInfo(int[] a, int l, int r) {
        if (l == r) {
            return new Status(a[l], a[l], a[l], a[l]);
        }
        int m = (l + r) >> 1;
        Status lSub = getInfo(a, l, m);
        Status rSub = getInfo(a, m + 1, r);
        return pushUp(lSub, rSub);
    }

    public Status pushUp(Status l, Status r) {
        int iSum = l.iSum + r.iSum;
        int lSum = Math.max(l.lSum, l.iSum + r.lSum);
        int rSum = Math.max(r.rSum, r.iSum + l.rSum);
        int mSum = Math.max(Math.max(l.mSum, r.mSum), l.rSum + r.lSum);
        return new Status(lSum, rSum, mSum, iSum);
    }

}
