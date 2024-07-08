package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 392. 判断子序列
 * https://leetcode.cn/problems/is-subsequence/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-01
 */

public class IsSubsequence {

    public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }
}
