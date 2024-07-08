package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 97. 交错字符串
 * https://leetcode.cn/problems/interleaving-string/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-24
 */

public class InterleavingString {

    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();

        if (n + m != t) {
            return false;
        }

        boolean[] f = new boolean[m + 1];

        f[0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) {
                    f[j] = f[j] && s1.charAt(i - 1) == s3.charAt(p);
                }
                if (j > 0) {
                    f[j] = f[j] || (f[j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }

        return f[m];
    }
}
