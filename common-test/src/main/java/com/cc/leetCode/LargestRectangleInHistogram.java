package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;

/**
 * Title: LargestRectangleInHistogram
 * Description: LargestRectangleInHistogram
 * 84. 柱状图中最大的矩形
 * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 * Date:  2019/8/2
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class LargestRectangleInHistogram {

    public static void main(String[] args) {
        largestRectangleAreaTest("[2,1,5,6,2,3]");
        largestRectangleAreaTest("[0,0,0,0,0,0,0,0,2147483647]");
    }

    public static void largestRectangleAreaTest(String s) {
        int[] heights = StringToArrayUtil.oneDimensionArr(s);
        System.out.println(new LargestRectangleInHistogram().largestRectangleArea(heights));
    }


    public int largestRectangleArea(int[] heights) {

        if (heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = Integer.MIN_VALUE;

        for (int i = 0; i < heights.length; i++) {
            int minHeight = Integer.MAX_VALUE;
            int maxHeight = Integer.MIN_VALUE;
            for (int j = i; j < heights.length; j++) {
                if (j == i) {
                    minHeight = heights[j];
                    maxHeight = heights[j];
                } else {
                    minHeight = minHeight < heights[j] ? minHeight : heights[j];
                    maxHeight = maxHeight > heights[j] ? maxHeight : heights[j];
                }
                int tmp = (j - i + 1) * minHeight;
                maxArea = maxArea > tmp ? maxArea : tmp;
            }
            if (maxArea >= (long)(heights.length - i) * maxHeight) {
                return maxArea;
            }
        }
        return maxArea;
    }

    public int largestRectangleArea2(int[] heights) {

        if (heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = Integer.MIN_VALUE;

        int[][] minHeight = new int[heights.length][heights.length];
        for (int i = 0; i < heights.length; i++) {
            for (int j = i; j < heights.length; j++) {
                if (j == i) {
                    minHeight[i][j] = heights[j];
                } else {
                    minHeight[i][j] = minHeight[i][j - 1] < heights[j] ? minHeight[i][j - 1] : heights[j];
                }
                int tmp = (j - i + 1) * minHeight[i][j];
                maxArea = maxArea > tmp ? maxArea : tmp;
            }
        }
        return maxArea;
    }
}
