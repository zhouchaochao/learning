package com.cc.leetCode;


import java.util.Arrays;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: DistinctSubsequences
 * 115. 不同的子序列
 * https://leetcode.cn/problems/distinct-subsequences/
 * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数。
 * 题目数据保证答案符合 32 位带符号整数范围。
 * 示例 1：
 * 输入：s = "rabbbit", t = "rabbit"
 * 输出：3
 * 解释：
 * 如下所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
 * rabbbit
 * rabbbit
 * rabbbit
 *
 * @date: 2023-08-25
 */

public class DistinctSubsequences {

    public static void main(String[] args) {
        System.out.println(new DistinctSubsequences().numDistinct("rabbbit","rabbit"));
        System.out.println(new DistinctSubsequences().numDistinct("babgbag","bag"));
        System.out.println(new DistinctSubsequences().numDistinct("adbdadeecadeadeccaeaabdabdbcdabddddabcaaadbabaaedeeddeaeebcdeabcaaaeeaeeabcddcebddebeebedaecccbdcbcedbdaeaedcdebeecdaaedaacadbdccabddaddacdddc","bcddceeeebecbc"));
    }

    public int numDistinct(String s, String t) {
        //return numDistinct1(s, 0, t, 0);
        return numDistinct2(s, t);
    }

    public int numDistinct1(String s, int indexS, String t, int indexT) {
        if (t.length() == indexT) {
            return 1;
        }
        if (s.length() == indexS) {
            return 0;
        }

        if (s.charAt(indexS) == t.charAt(indexT)) {
            return numDistinct1(s, indexS + 1, t, indexT + 1) + numDistinct1(s, indexS + 1, t, indexT);
        } else {
            return numDistinct1(s, indexS + 1, t, indexT);
        }
    }

    // s = "aaabbbccc"//j=9
    // t = "abc" //i=3
    // a a a b b b c c c
    // c
    // b
    // a
    // dp[3][9] //其实换成dp[9][3],可能更好理解
    // 定义:
    // dp[i][j] 表示字符串s.sub(j)包含t.sub(i)子序列的个数
    // 状态转移
    // dp[i][j] = (s.charAt(j) == t.charAt(i)) ? (dp[i+1][j+1] + dp[i][j+1]) : dp[i][j+1]
    // 最终求dp[0][0]
    // 最后一行 i=t.length()-1,s中找t最后一个字符
    // 最后一列 j=s.length()-1,s最后一个字符中匹配t1,t2,t3 //最后一列除了最底下一行，其他都可以置为0，因为t2,t3长度增长，肯定匹配不到

    public int numDistinct2(String s, String t) {
        int[][] dp = new int[t.length()][s.length()];

        //初始化最后一行
        //int i = t.length()-1;
        char endT = t.charAt(t.length()-1);
        char endS = s.charAt(s.length()-1);
        if (endT == endS) {
            dp[t.length()-1][s.length()-1] = 1;
        } else {
            dp[t.length()-1][s.length()-1] = 0;
        }
        for (int j = s.length()-2; j >= 0; j--) {
            if (s.charAt(j) == endT) {
                dp[t.length()-1][j] = 1 + dp[t.length()-1][j+1];
            } else {
                dp[t.length()-1][j] = dp[t.length()-1][j+1];
            }
        }

        // 从下往上，从右到左，逐步dp
        for (int i = t.length() - 2; i >= 0; i--) {
            for (int j = s.length() - 2; j >= 0; j--) {
                if (s.charAt(j) == t.charAt(i)) {
                    dp[i][j] = dp[i + 1][j + 1] + dp[i][j + 1];
                } else {
                    dp[i][j] = dp[i][j + 1];
                }
            }
        }
        //printDp(dp);
        return dp[0][0];
    }

    private void printDp(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
    }
}
