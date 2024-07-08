package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 74. 搜索二维矩阵
 * https://leetcode.cn/problems/search-a-2d-matrix/description/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-12
 */

public class SearchA2dMatrix {

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int low = 0, high = m * n - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            int x = matrix[mid / n][mid % n];
            if (x < target) {
                low = mid + 1;
            } else if (x > target) {
                high = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
