package com.cc.leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 739. 每日温度
 * https://leetcode.cn/problems/daily-temperatures/description/?envType=study-plan-v2&envId=top-100-liked
 * @date: 2024-06-10
 */

public class DailyTemperatures {

    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        int[] ans = new int[length];
        Deque<Integer> stack = new LinkedList<Integer>();
        for (int i = 0; i < length; i++) {
            int temperature = temperatures[i];
            while (!stack.isEmpty() && temperature > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                ans[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return ans;
    }
}
