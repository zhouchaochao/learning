package com.cc.leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 128. 最长连续序列
 * https://leetcode.cn/problems/longest-consecutive-sequence/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-03
 */

public class LongestConsecutiveSequence {

    public int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}
