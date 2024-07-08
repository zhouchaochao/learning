package com.cc.leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 202. 快乐数
 * https://leetcode.cn/problems/happy-number/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-03
 */

public class HappyNumber {

    private int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = getNext(n);
        }
        return n == 1;
    }
}
