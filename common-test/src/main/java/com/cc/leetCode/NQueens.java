package com.cc.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: NQueens
 * Description: NQueens
 * 51. N皇后
 * https://leetcode-cn.com/problems/n-queens/
 * Date:  2019/4/9
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class NQueens {

    public static void main(String[] args) {
        List<List<String>> solution = new NQueens().solveNQueens(4);
        System.out.println(solution);
    }

    public List<List<String>> solveNQueens(int n) {
        int[][] arr = new int[n][n];
        List<List<String>> solution = new ArrayList<>();
        boolean[] columnHasQueue = new boolean[n];
        addQueen(arr, 0, solution, columnHasQueue);
        return solution;
    }

    public void addQueen(int[][] arr, int row, List<List<String>> solution, boolean[] columnHasQueue) {
        if (row == arr.length) {
            solution.add(createSolution(arr));
            return;
        }
        for (int j = 0; j < arr[0].length; j++) {
            if (canSetQueen(arr, row, j, columnHasQueue)) {
                setQueue(arr, row, j, columnHasQueue);//当前位置设置皇后
                addQueen(arr, row + 1, solution, columnHasQueue);//递归探索下一行
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

    private List<String> createSolution(int[][] arr) {
        List<String> solution = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            StringBuilder line = new StringBuilder(arr.length);
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 0) {
                    line.append(".");
                } else {
                    line.append("Q");
                }
            }
            solution.add(line.toString());
        }
        return solution;
    }
}
