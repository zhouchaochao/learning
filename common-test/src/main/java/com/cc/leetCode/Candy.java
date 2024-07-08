package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: Candy
 * Description: Candy
 * 135. 分发糖果
 * https://leetcode.cn/problems/candy/?envType=study-plan-v2&envId=top-interview-150
 * Date:  2019/4/2
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class Candy {

    private static final Logger logger = LoggerFactory.getLogger(Candy.class);

    public static void main(String[] args) {
        int[] candyReached = new int[]{1,0,2};
        System.out.println(java.util.Arrays.toString(candyReached));
        System.out.println("" + new Candy().candy(candyReached));

        candyReached = new int[]{1,2,2};
        System.out.println(java.util.Arrays.toString(candyReached));
        System.out.println("" + new Candy().candy(candyReached));
    }

    public int candy2(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0, ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }
        return ret;
    }


    public int candy(int[] ratings) {
        int[] candyReached = new int[ratings.length];
        candy(ratings, 0, ratings.length - 1, candyReached);
        int sum = 0;
        for (int i = 0; i < candyReached.length; i++) {
            sum += candyReached[i];
        }
        System.out.println(java.util.Arrays.toString(candyReached));
        return sum;
    }

    public void candy(int[] ratings, int x, int y, int[] candyReached) {
        //System.out.println(x + ":" + y);
        if (x == y) {
            if (candyReached[x] == 0) {
                candyReached[x] = 1;
            }
            fixValue(ratings, x, candyReached);
            return;
        }
        if (x < 0 || y >= ratings.length) {
            return;
        }

        int minValue = ratings[x];
        int minIndex = x;
        for (int i = x; i <= y; i++) {
            if (minValue > ratings[i]) {
                minValue = ratings[i];
                minIndex = i;
            }
        }
        candyReached[minIndex] = 1;//最小分数给1颗糖果
        fixValue(ratings, minIndex, candyReached);
        candyLeft(ratings, minIndex - 1, candyReached);
        candyRight(ratings, minIndex + 1, candyReached);
    }

    //从x开始往左分配
    public void candyLeft(int[] ratings, int x, int[] candyReached) {
        if (x < 0) {
            return;
        }
        if (candyReached[x] > 0) {//已经分批了
            fixValue(ratings, x, candyReached);
            return;
        } else {//未分配
            if (ratings[x] > ratings[x + 1]) {//分数比右侧大
                candyReached[x] = candyReached[x + 1] + 1;
                candyLeft(ratings, x - 1, candyReached);
            } else {//分数比右侧相等或小一些
                int leftSide = findLeftSide(x, candyReached);
                candy(ratings, leftSide, x, candyReached);
                return;
            }
        }
    }

    //从x开始往右分配
    public void candyRight(int[] ratings, int x, int[] candyReached) {
        if (x >= ratings.length) {
            return;
        }
        if (candyReached[x] > 0) {//已经分批了
            fixValue(ratings, x, candyReached);
            return;
        } else {//未分配
            if (ratings[x] > ratings[x - 1]) {//分数比左侧大
                candyReached[x] = candyReached[x - 1] + 1;
                candyRight(ratings, x + 1, candyReached);
            } else {//分数比右侧相等或小一些
                int rightSide = findRightSide(x, candyReached);
                candy(ratings, x, rightSide, candyReached);
                return;
            }
        }
    }

    //从x开始往左找未分配的位置
    public int findLeftSide(int x, int[] candyReached) {
        int i = x;
        for (; i >= 0; i--) {
            if (candyReached[i] == 0) {
                continue;
            } else {//已经分配了
                i = i + 1;
                break;
            }
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    //从x开始往右找未分配的位置
    public int findRightSide(int x, int[] candyReached) {
        int i = x;
        for (; i < candyReached.length; i++) {
            if (candyReached[i] == 0) {
                continue;
            } else {//已经分配了
                i = i - 1;
                break;
            }
        }
        if (i == candyReached.length) {
            i = candyReached.length - 1;
        }
        //System.out.println("findRightSide:"+i);
        return i;
    }

    //根据左右的值和当前的值，调整三个值
    public void fixValue(int[] ratings, int x, int[] candyReached) {
        //右侧已经分配了
        if (x < (ratings.length - 1) && candyReached[x + 1] > 0) {
            if (ratings[x] > ratings[x + 1]) {
                if (candyReached[x] <= candyReached[x + 1]) {
                    candyReached[x] = candyReached[x + 1] + 1;
                }
            } else if (ratings[x] < ratings[x + 1]) {
                if (candyReached[x] >= candyReached[x + 1]) {
                    candyReached[x + 1] = candyReached[x] + 1;
                }
            }
        }

        //左侧已经分配了
        if (x > 1 && candyReached[x - 1] > 0) {
            if (ratings[x] > ratings[x - 1]) {
                if (candyReached[x] <= candyReached[x - 1]) {
                    candyReached[x] = candyReached[x - 1] + 1;
                }
            } else if (ratings[x] < ratings[x - 1]) {
                if (candyReached[x] >= candyReached[x - 1]) {
                    candyReached[x - 1] = candyReached[x] + 1;
                }
            }
        }
    }
}
