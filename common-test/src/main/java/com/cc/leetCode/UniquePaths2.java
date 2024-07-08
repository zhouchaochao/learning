package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 63. 不同路径 II
 * https://leetcode.cn/problems/unique-paths-ii/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-24
 */

public class UniquePaths2 {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length, m = obstacleGrid[0].length;
        int[] f = new int[m];

        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                //if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                if (j - 1 >= 0) {
                    f[j] += f[j - 1];
                }
            }
        }

        return f[m - 1];
    }
}
