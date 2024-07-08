package com.cc.leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Title: NQueens2
 * Description: NQueens2
 * 52. N皇后 II
 * https://leetcode-cn.com/problems/n-queens-ii/
 * Date:  2019/7/23
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class NQueens2 {

    public static Integer SOLUTION_COUNT = 0;

    public static void main(String[] args) {
        System.out.println(new NQueens2().totalNQueens(4));
    }

    public int totalNQueens(int n) {
        SOLUTION_COUNT = 0;
        int[][] arr = new int[n][n];
        boolean[] columnHasQueue = new boolean[n];
        addQueen(arr, 0, columnHasQueue);
        return SOLUTION_COUNT;
    }

    public void addQueen(int[][] arr, int row, boolean[] columnHasQueue) {
        if (row == arr.length) {
            SOLUTION_COUNT++;
            return;
        }
        for (int j = 0; j < arr[0].length; j++) {
            if (canSetQueen(arr, row, j, columnHasQueue)) {
                setQueue(arr, row, j, columnHasQueue);//当前位置设置皇后
                addQueen(arr, row + 1, columnHasQueue);//递归探索下一行
                removeQueue(arr, row, j, columnHasQueue);//清理皇后
            }
        }
    }

    /**
     * 校验（i，j）位置是否能放置皇后
     *
     * @param arr
     * @param row
     * @param column
     * @param columnHasQueue 记录这一列是否有皇后
     * @return
     */
    public boolean canSetQueen(int[][] arr, int row, int column, boolean[] columnHasQueue) {
        //检查列冲突
        /*for (int i = 0; i < row; i++) {
            if (arr[i][column] == 1) {
                return false;
            }
        }*/
        if (columnHasQueue[column]) {
            return false;
        }

        //检查左上到右下的斜线冲突
        int i = row - 1;
        int j = column - 1;
        while (i >= 0 && j >= 0) {
            if (arr[i][j] == 1) {
                return false;
            }
            i = i - 1;
            j = j - 1;
        }

        //检查左下到右上的斜线冲突
        i = row - 1;
        j = column + 1;
        while (i >= 0 && j < arr[0].length) {
            if (arr[i][j] == 1) {
                return false;
            }
            i = i - 1;
            j = j + 1;
        }
        return true;
    }

    private void setQueue(int[][] arr, int i, int j, boolean[] columnHasQueue) {
        arr[i][j] = 1;
        columnHasQueue[j] = true;
    }

    private void removeQueue(int[][] arr, int i, int j, boolean[] columnHasQueue) {
        arr[i][j] = 0;
        columnHasQueue[j] = false;
    }

    public int totalNQueens2(int n) {
        Set<Integer> columns = new HashSet<Integer>();
        Set<Integer> diagonals1 = new HashSet<Integer>();
        Set<Integer> diagonals2 = new HashSet<Integer>();
        return backtrack(n, 0, columns, diagonals1, diagonals2);
    }

    public int backtrack(int n, int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        if (row == n) {
            return 1;
        } else {
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (columns.contains(i)) {
                    continue;
                }
                int diagonal1 = row - i;
                if (diagonals1.contains(diagonal1)) {
                    continue;
                }
                int diagonal2 = row + i;
                if (diagonals2.contains(diagonal2)) {
                    continue;
                }
                columns.add(i);
                diagonals1.add(diagonal1);
                diagonals2.add(diagonal2);
                count += backtrack(n, row + 1, columns, diagonals1, diagonals2);
                columns.remove(i);
                diagonals1.remove(diagonal1);
                diagonals2.remove(diagonal2);
            }
            return count;
        }
    }

}
