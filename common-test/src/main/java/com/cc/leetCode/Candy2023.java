package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: Candy2023
 *  https://leetcode.cn/problems/candy/
 *  135. 分发糖果
 * @date: 1/3/23
 */

public class Candy2023 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Candy2023.class);

    public static void main(String[] args) {
        int[] candyReached = new int[]{1,0,2};
        System.out.println(java.util.Arrays.toString(candyReached));
        System.out.println("" + new Candy2023().candy(candyReached));

        candyReached = new int[]{1,2,2};
        System.out.println(java.util.Arrays.toString(candyReached));
        System.out.println("" + new Candy2023().candy(candyReached));

        // 1,2,3,4,2,1,2
        candyReached = new int[]{0,1,2,5,3,2,7};
        System.out.println(java.util.Arrays.toString(candyReached));
        System.out.println("" + new Candy2023().candy(candyReached));
    }

    /**
     * 算法：
     * 说明：v(i)表示分配给i的糖果数，s(i)表示i位置的分数
     * 初始化v(0)=1，i=0
     * 循环计算i+1位置的值v(i+1)
     *   如果1 s(i+1) > s(i)  // 当前i位置的糖果基础上+1
     *     v(i+1) = v(i) + 1;
     *     i++;
     *   如果2 s(i+1) = s(i) // 和i没有相关性，取最低值1
     *     v(i+1) = 1;
     *     i++;
     *   如果3 s(i+1) < s(i), // v(i+1)暂时未知，还需要推测，但是最后需要满足 v(i+1) < v(i)
     *     继续往前找到下降的拐点j(满足:s(j+1)>=s(j))
     *     赋值：v(j) = 1, v(j-1) =2, v(j-2) =3, .... , v(i+1) = ...
     *     修复可能不合理的v(i)
     *       if(v(i) <= v(i+1))
     *         v(i) = v(i+1) + 1
     *      vi = 1;
     *     i=j;
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        // i位置的值
        int vi = 1;
        // 总糖果数
        int sum = 1;
        for (int i = 0; i < ratings.length-1; ) {
            if (ratings[i+1] > ratings[i]) {
                vi = vi + 1;
                //System.out.println((i+1) + " -> " + vi);
                sum += vi;
                i++;
            } else if(ratings[i+1] == ratings[i]) {
                vi = 1;
                //System.out.println((i+1) + " -> " + vi);
                sum += vi;
                i++;
            } else {
                // 找到下降拐点j，使得s(j+1) >= s(j)
                int j = i + 1;
                while (j + 1 < ratings.length && ratings[j + 1] < ratings[j]) {
                    j++;
                }
                //System.out.println("拐点：" + j);
                // 从i+1 -> j 是一路下降，所以可以赋值v(j) = 1, v(j-1) =2,.... , v(i+1) = j-(i+1)+1
                // i+1位置的糖果数v(i+1)
                int vi1 = j-i;
                sum += vi1 * (vi1 + 1) / 2;
                if (vi <= vi1) {
                    //System.out.println("修复:" + vi + " -> " + (vi1+1));
                    sum+= (vi1+1) -vi;
                }
                vi = 1;
                i = j;
            }
        }
        return sum;
    }



}
