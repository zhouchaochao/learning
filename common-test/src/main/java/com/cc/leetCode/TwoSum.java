package com.cc.leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 1. 两数之和
 * https://leetcode.cn/problems/two-sum/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-03
 */

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }
}
