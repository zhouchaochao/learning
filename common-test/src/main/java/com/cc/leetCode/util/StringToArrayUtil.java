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

}
