package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;
import jdk.internal.org.objectweb.asm.util.TraceAnnotationVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: TrappingRainWater
 * Description: TrappingRainWater
 * 42. 接雨水
 * https://leetcode-cn.com/problems/trapping-rain-water/
 * Date:  2019/4/9
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class TrappingRainWater {

    private static final Logger logger = LoggerFactory.getLogger(TrappingRainWater.class);

    public static void main(String[] args) {
        String input = "[0,1,0,2,1,0,1,3,2,1,2,1]";
        int[] height = StringToArrayUtil.oneDimensionArr(input);
        System.out.println(new TrappingRainWater().trap(height));
    }

    public int trap(int[] height) {

        if (height == null) {
            return 0;
        }
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];//可以考虑复用leftMax，去掉rightMax

        for (int i = 0; i < height.length; i++) {
            if (i == 0) {
                leftMax[i] = height[i];
                continue;
            }
            leftMax[i] = leftMax[i - 1] > height[i] ? leftMax[i - 1] : height[i];
        }

        int sum = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            if (i == height.length - 1) {
                rightMax[i] = height[i];
                continue;
            }
            rightMax[i] = rightMax[i + 1] > height[i] ? rightMax[i + 1] : height[i];
            if (leftMax[i] > height[i] && rightMax[i] > height[i]) {
                sum += (leftMax[i] > rightMax[i] ? rightMax[i] - height[i] : leftMax[i] - height[i]);
            }
        }

        return sum;
    }
}
