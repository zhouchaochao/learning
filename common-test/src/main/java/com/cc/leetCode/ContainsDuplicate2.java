package com.cc.leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 219. 存在重复元素 II
 * https://leetcode.cn/problems/contains-duplicate-ii/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-03
 */

public class ContainsDuplicate2 {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<Integer>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (i > k) {
                set.remove(nums[i - k - 1]);
            }
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }
}
