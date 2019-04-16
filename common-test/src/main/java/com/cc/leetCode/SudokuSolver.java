package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;

/**
 * Title: SudokuSolver
 * Description: SudokuSolver
 * https://leetcode-cn.com/problems/sudoku-solver/
 * 37. 解数独
 * Date:  2019/4/5
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class SudokuSolver {

    public static void main(String[] args) {

        System.out.println("请输入数独：");
        String input = "[[\"5\",\"3\",\".\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\"6\",\".\",\".\",\"1\",\"9\",\"5\",\".\",\".\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\".\",\"6\",\".\"],[\"8\",\".\",\".\",\".\",\"6\",\".\",\".\",\".\",\"3\"],[\"4\",\".\",\".\",\"8\",\".\",\"3\",\".\",\".\",\"1\"],[\"7\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\",\"6\"],[\".\",\"6\",\".\",\".\",\".\",\".\",\"2\",\"8\",\".\"],[\".\",\".\",\".\",\"4\",\"1\",\"9\",\".\",\".\",\"5\"],[\".\",\".\",\".\",\".\",\"8\",\".\",\".\",\"7\",\"9\"]]";
        //System.out.println(input);
        char[][] board = StringToArrayUtil.twoDimensionCharArr(input);
        print(board);
        System.out.println("结果：");

        SudokuSolver object = new SudokuSolver();
        object.solveSudoku(board);
        //object.solveSudoku2(board);
        print(board);
    }

    public void solveSudoku2(char[][] board) {
        ValueState[] lines = new ValueState[9];//每一行拥有的数据
        ValueState[] columns = new ValueState[9];//每一列拥有的数据
        NineCell[][] nineCellArr = new NineCell[3][3];

        preProcess(board, lines, columns, nineCellArr);
        //printStatus(lines,columns,nineCellArr);

        boolean hasCertain = true;
        while (hasCertain) {
            hasCertain = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == '.') {
                        java.util.Set<Character> possibleValues = calculatePossibleValues(i, j, lines, columns, nineCellArr);
                        if (possibleValues.size() == 1) {//确定值
                            Character certainValue = possibleValues.iterator().next();
                            hasCertain = true;
                            certainValue(i, j, certainValue, board, lines, columns, nineCellArr);
                        }
                    }
                }
            }
        }
    }


    public void solveSudoku(char[][] board) {
        ValueState[] lines = new ValueState[9];//每一行拥有的数据
        ValueState[] columns = new ValueState[9];//每一列拥有的数据
        NineCell[][] nineCellArr = new NineCell[3][3];
        preProcess(board, lines, columns, nineCellArr);
        //printStatus(lines,columns,nineCellArr);
        solveSudoku(board, 0, lines, columns, nineCellArr);
    }

    /**
     * 从 startLine 行开始数独求解。计算完成返回true，计算冲突返回false
     *
     * @param board
     * @param startLine
     * @param lines
     * @param columns
     * @param nineCellArr
     * @return
     */
    public boolean solveSudoku(char[][] board, int startLine, ValueState[] lines, ValueState[] columns, NineCell[][] nineCellArr) {
        for (int i = startLine; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    java.util.Set<Character> possibleValues = calculatePossibleValues(i, j, lines, columns, nineCellArr);
                    if (possibleValues.size() == 0) {//当前位置计算不出，说明冲突了，不对
                        return false;
                    }
                    for (Character item : possibleValues) {
                        certainValue(i, j, item, board, lines, columns, nineCellArr);//先拿一个值确定当前位置
                        if (solveSudoku(board, i, lines, columns, nineCellArr)) {//动态规划对后续求解
                            return true;
                        } else {
                            rollbackValue(i, j, board, lines, columns, nineCellArr);
                        }
                    }
                    return false;//当前位置的所有值都不对，说明推断错误
                }
            }
        }
        //所有位置都不是'.'，说明数独求解结束了
        //System.out.println("最后结束了？");
        return true;
    }


    //确认一个格子的数据
    public void certainValue(int i, int j, char certainValue, char[][] board, ValueState[] lines, ValueState[] columns, NineCell[][] nineCellArr) {
        board[i][j] = certainValue;
        nineCellArr[i / 3][j / 3].valueState.add(certainValue);
        lines[i].add(certainValue);
        columns[j].add(certainValue);
    }

    //回滚一个格子的数据
    public void rollbackValue(int i, int j, char[][] board, ValueState[] lines, ValueState[] columns, NineCell[][] nineCellArr) {
        char rollbackValue = board[i][j];
        board[i][j] = '.';
        nineCellArr[i / 3][j / 3].valueState.remove(rollbackValue);
        lines[i].remove(rollbackValue);
        columns[j].remove(rollbackValue);
    }

    public void preProcess(char[][] board, ValueState[] lines, ValueState[] columns, NineCell[][] nineCellArr) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                nineCellArr[i][j] = new NineCell(3 * i, 3 * j, board);
            }
        }

        for (int i = 0; i < 9; i++) {
            lines[i] = new ValueState();
            for (int j = 0; j < 9; j++) {
                if (i == 0) {
                    columns[j] = new ValueState();
                }
                if (board[i][j] != '.') {
                    lines[i].add(board[i][j]);
                    columns[j].add(board[i][j]);
                }
            }
        }
    }


    //九宫格
    class NineCell {
        ValueState valueState;

        public NineCell(int beginx, int beginy, char[][] board) {
            valueState = new ValueState();

            for (int i = beginx; i < beginx + 3; i++) {
                for (int j = beginy; j < beginy + 3; j++) {
                    if (board[i][j] != '.') {
                        valueState.add(board[i][j]);
                    }
                }
            }
        }
    }

    //每个9宫格或者每一行每一列的数据状态,主要记录缺失的数据
    class ValueState {
        java.util.Set<Character> missingValues = new java.util.HashSet<Character>();

        public ValueState() {
            missingValues.add('1');
            missingValues.add('2');
            missingValues.add('3');
            missingValues.add('4');
            missingValues.add('5');
            missingValues.add('6');
            missingValues.add('7');
            missingValues.add('8');
            missingValues.add('9');
        }

        public void add(char var) {
            missingValues.remove(var);
        }

        public void remove(char var) {
            missingValues.add(var);
        }

        public void print() {
            System.out.println("缺失：" + missingValues.toString());
        }
    }

    //计算位置（i,j）可能存在的数据。根据行，列，所在九宫格三个数据求交集
    public java.util.Set<Character> calculatePossibleValues(int i, int j, ValueState[] lines, ValueState[] columns, NineCell[][] nineCellArr) {
        java.util.Set<Character> one = nineCellArr[i / 3][j / 3].valueState.missingValues;
        java.util.Set<Character> two = lines[i].missingValues;
        java.util.Set<Character> three = columns[j].missingValues;
        java.util.Set<Character> result = new java.util.HashSet<>();
        for (Character item : one) {
            if (two.contains(item) && three.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static void print(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            String line = "[";
            for (int j = 0; j < board[i].length; j++) {
                line += board[i][j] + ",";
            }
            line = line.substring(0, line.length() - 1);
            line += "]";
            System.out.println(line);
        }
    }

    public void printStatus(ValueState[] lines, ValueState[] columns, NineCell[][] nineCellArr) {
        for (int i = 0; i < 9; i++) {
            System.out.println("行" + i);
            lines[i].print();
        }
        for (int i = 0; i < 9; i++) {
            System.out.println("列" + i);
            columns[i].print();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("九宫格" + i + "," + j);
                nineCellArr[i][j].valueState.print();
            }
        }
    }
}
