package com.cc.leetCode.util;

import com.cc.leetCode.DungeonGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: StringToArrayUtil
 * Description: StringToArrayUtil
 * Date:  2019/4/2
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class StringToArrayUtil {

    private static final Logger logger = LoggerFactory.getLogger(StringToArrayUtil.class);

    /**
     * 转换成二维数组
     *
     * @param input "[[-2,-3,3],[-5,-10,1],[10,30,-5]]"
     * @return
     */
    public static int[][] twoDimensionArr(String input) {
        input = input.replaceAll("\\[\\[", "");
        input = input.replaceAll("\\]\\]", "");
        String[] lines = input.split("\\],\\[");
        int[][] dungeon = new int[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            String lineStr = lines[i];
            String[] oneLineStrArr = lineStr.split(",");
            int[] oneLine = new int[oneLineStrArr.length];
            for (int j = 0; j < oneLineStrArr.length; j++) {
                oneLine[j] = Integer.valueOf(oneLineStrArr[j]);
            }
            dungeon[i] = oneLine;
            System.out.println(java.util.Arrays.toString(oneLine));
        }
        return dungeon;
    }

    /**
     * 转换成一维数组
     *
     * @param input "[-2,-3,3]"
     * @return
     */
    public static int[] oneDimensionArr(String input) {
        input = input.replaceAll("\\[", "");
        input = input.replaceAll("\\]", "");
        String[] itmes = input.split(",");
        int[] arr = new int[itmes.length];
        for (int j = 0; j < itmes.length; j++) {
            arr[j] = Integer.valueOf(itmes[j]);
        }
        return arr;
    }

    /**
     * 转换成二维数组
     *
     * @param input [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
     * @return
     */
    public static char[][] twoDimensionCharArr(String input) {
        input = input.replaceAll("\\[\\[", "");
        input = input.replaceAll("\\]\\]", "");
        input = input.replaceAll("\"","");
        String[] lines = input.split("\\],\\[");
        char[][] dungeon = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            String lineStr = lines[i];
            String[] oneLineStrArr = lineStr.split(",");
            char[] oneLine = new char[oneLineStrArr.length];
            for (int j = 0; j < oneLineStrArr.length; j++) {
                oneLine[j] = oneLineStrArr[j].charAt(0);
            }
            dungeon[i] = oneLine;
        }
        return dungeon;
    }

}
