package com.cc.leetCode;

import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: CountOfSmallerNumbersAfterSelf
 *          315. 计算右侧小于当前元素的个数
 *          https://leetcode.cn/problems/count-of-smaller-numbers-after-self/
 * @date: 2023-07-07
 */

public class CountOfSmallerNumbersAfterSelf {

    // dp[i][j]  nxn
    // num[]  n
    // dp[i][j] 表示 num 中 i 位置右侧小于值 num[j] 的个数
    // counts[i] = dp[i][i] 那么最终要求的结果就是 dp[0][0], dp[1][1]..... dp[n-1][n-1],
    /**
     * 状态转移：
     * 如果 num[i+1] < num[j]，那么
     *      dp[i][j] = 1 + dp[i+1][j]
     * 如果 num[i+1] >= num[j]，那么
     *      dp[i][j] = dp[i+1][j]
     *
     * 边界：
     * dp[n-1][j] = 0   j∈[0,n-1]
     *
     * 步骤：（每一行到对角线就OK）
     * dp[n-1][n-1],
     * dp[n-1][n-2], dp[n-2][n-2],
     * dp[n-1][n-3], dp[n-2][n-3], dp[n-3][n-3],
     * dp[n-1][n-4], dp[n-2][n-4], dp[n-3][n-4], dp[n-4][n-4],
     *
     *
     * dp[n-1][0], dp[n-2][0], dp[n-3][0], dp[n-4][0], dp[n-5][0],.... dp[0][0]
     */

    //直接暴力，遍历就行啊？事件复杂度 n*n，和dp一样
    public static void main(String[] args) {
        String[] arr = new String[]{"hello", "world", "ok"};
        System.out.println(String.format("abc_%s_%s", "hello", "world"));
        System.out.println(String.format("abc_%s_%s", arr));
    }

    public List<Integer> countSmaller(int[] nums) {
        return null;
    }

}
