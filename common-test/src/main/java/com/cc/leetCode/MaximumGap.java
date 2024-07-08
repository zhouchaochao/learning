package com.cc.leetCode;


import java.util.Arrays;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: MaximumGap
 * https://leetcode.cn/problems/maximum-gap/
 * 164. 最大间距
 *
 * 给定一个无序的数组 nums，返回 数组在排序之后，相邻元素之间最大的差值 。如果数组元素个数小于 2，则返回 0 。您必须编写一个在「线性时间」内运行并使用「线性额外空间」的算法。
 * 输入: nums = [3,6,9,1]
 * 输出: 3
 *
 * @date: 2023-06-15
 */

public class MaximumGap {

    public static void main(String[] args) {
        int[] arr = new int[]{3,6,9,1};
        System.out.println(new MaximumGap().maximumGap(arr));
    }

    // 基数排序
    public int maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        long exp = 1;
        // 中间环节排序好的数组
        int[] buf = new int[n];
        int maxVal = Arrays.stream(nums).max().getAsInt();

        while (maxVal >= exp) {
            // index 表示0-9的数值，cnt[index]表示计数的个数，(cnt[index]-1) 表示index在排序序列中的下标
            int[] cnt = new int[10];
            for (int i = 0; i < n; i++) {
                // 0-9，原始数第x位置的数值，用来进行基数排序
                int digit = (nums[i] / (int) exp) % 10;
                cnt[digit]++;
            }
            for (int i = 1; i < 10; i++) {
                cnt[i] += cnt[i - 1];
            }
            // 从后往前，保证稳定
            for (int i = n - 1; i >= 0; i--) {
                int digit = (nums[i] / (int) exp) % 10;
                buf[cnt[digit] - 1] = nums[i];
                cnt[digit]--;
            }
            System.arraycopy(buf, 0, nums, 0, n);
            // 先按照个位排，再按照十位来排
            exp *= 10;
        }

        int ret = 0;
        for (int i = 1; i < n; i++) {
            ret = Math.max(ret, nums[i] - nums[i - 1]);
        }
        return ret;
    }


    // 超出内存限制
    public int maximumGap3(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        // 数组最大值
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max = max > nums[i] ? max : nums[i];
        }

        // index数组下标 i 表示 nums 中的 value， index[i] 表示 value 的个数
        int[] index = new int[max+1];
        for (int i = 0; i < nums.length; i++) {
            index[nums[i]]++;
        }

        // index数组下标 i 表示 nums 中的 value， index[i] 表示 value 在新排序后数组中的 (下标 + 1)
        for (int i = 1; i < index.length; i++) {
            index[i] = index[i] + index[i-1];
        }

        int[] newNums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            newNums[index[nums[i]] - 1] = nums[i];
            index[nums[i]] = index[nums[i]] - 1;
        }

        int maxGap = newNums[1] - newNums[0];
        for (int i = 2; i < nums.length; i++) {
            maxGap = maxGap > newNums[i] - newNums[i - 1] ? maxGap : newNums[i] - newNums[i - 1];
        }
        return maxGap;
    }


    // 超出内存限制
    public int maximumGap2(int[] nums) {
        if (nums==null || nums.length < 2) {
            return 0;
        }

        // 数组最大值
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max = max > nums[i] ? max : nums[i];
        }

        // index数组下标 i 表示 nums 中的 value， index[i] 表示 value 的个数
        int[] index = new int[max+1];
        for (int i = 0; i < nums.length; i++) {
            index[nums[i]]++;
        }

        int[] newNums = new int[nums.length];
        int x = newNums.length-1;
        // 从后向前遍历index
        for (int i = index.length-1; i >= 0; i--) {
            for (int j = 0; j < index[i]; j++) {
                newNums[x--] = i;
            }
        }

        int maxGap = newNums[1] - newNums[0];
        for (int i = 2; i < nums.length; i++) {
            maxGap = maxGap > newNums[i] - newNums[i - 1] ? maxGap : newNums[i] - newNums[i - 1];
        }
        return maxGap;
    }

    public int maximumGap1(int[] nums) {
        if (nums==null || nums.length < 2) {
            return 0;
        }

        Arrays.sort(nums);
        int maxGap = nums[1] - nums[0];
        for (int i = 2; i < nums.length; i++) {
            maxGap = maxGap > nums[i] - nums[i - 1] ? maxGap : nums[i] - nums[i - 1];
        }
        return maxGap;
    }
}
