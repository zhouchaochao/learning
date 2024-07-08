package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;
import jdk.internal.org.objectweb.asm.util.TraceAnnotationVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.LinkedList;

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

    public int trap2(int[] height) {
        int ans = 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (height[left] < height[right]) {
                ans += leftMax - height[left];
                ++left;
            } else {
                ans += rightMax - height[right];
                --right;
            }
        }
        return ans;
    }

    /**
     * 1个数组，一次遍历
     * 思路：先计算当前柱子左边最高的值，然后反向遍历求出当前柱子右边最高的值，如果当前柱子小于左边最高，又小于右边最高，说明是个坑，可以存雨水
     *
     * @param height
     * @return
     */
    public int trap1Arr(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        // 当前位置以及当前位置左边的部分的最大值
        int[] leftMax = new int[height.length];
        leftMax[0] = height[0];
        for (int i = 1; i < height.length; i++) {
            leftMax[i] = leftMax[i - 1] > height[i] ? leftMax[i - 1] : height[i];
        }

        int sum = 0;
        // 当前位置以及当前位置右边的部分的最大值
        int rightMax = height[height.length-1];
        // 两端的柱子上不可能存雨水，不用计算。
        for (int i = height.length - 2; i >= 1; i--) {
            if (rightMax <= height[i]) {
                rightMax = height[i];
            }
            if (leftMax[i] > height[i] && rightMax > height[i]) {
                sum += (leftMax[i] > rightMax ? rightMax - height[i] : leftMax[i] - height[i]);
            }
        }

        return sum;
    }

    /**
     * 单调栈思想：保证栈底元素最大，栈顶元素最小
     * 当前元素i 比较 栈顶元素j
     *  小于栈顶元素，入栈
     *  等于栈顶元素，入栈 // 不能忽略，可以构造多个相等的柱子形成齿状画图想想
     *  大于栈顶元素j，那么栈顶元素j出栈
     *      当前如果栈为空，i入栈
     *      如果存在栈顶元素k,那么j作为底，左边界k，右边界i，可以计算出一块横向雨水面积。继续元素i和栈顶元素比较。
     * @param height
     * @return
     */
    public int trap2Deque(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int sum = 0;

        Deque<Integer> stack = new LinkedList<Integer>();
        stack.addFirst(0);

        for (int i = 1; i < height.length; i++) {
            // 直到（栈空，或者当前元素小于等于栈顶）
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                // 当前元素比 top 大， top出栈
                Integer top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                // belowTop左边界， top为底，i右边界，形成一个雨水区域
                Integer belowTop = stack.peek();
                sum += (i - belowTop - 1) * (Math.min(height[i], height[belowTop]) - height[top]);
                // top出栈了，现在belowTop是栈顶
            }
            stack.addFirst(i);
        }

        return sum;
    }

    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int sum = 0;
        int left = 0;
        int right = height.length - 1;
        int leftMax = height[0];
        int rightMax = height[height.length - 1];
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            // left 右移
            if (height[left] <= height[right]) {

            } else {
                // right 左移

            }
        }
        return sum;
    }


        /**
         * 双数组，3次遍历，2个数组，时间复杂度，空间复杂度高
         *
         * @param height
         * @return
         */
    public int trap2Arr(int[] height) {

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
