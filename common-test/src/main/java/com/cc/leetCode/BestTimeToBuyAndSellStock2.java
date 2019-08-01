package com.cc.leetCode;


import com.cc.leetCode.util.StringToArrayUtil;

/**
 * Title: BestTimeToBuyAndSellStock2
 * Description: BestTimeToBuyAndSellStock2 你可以完成无数次交易。
 * 122. 买卖股票的最佳时机 II
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
 * Date:  2019/7/24
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BestTimeToBuyAndSellStock2 {

    public static void main(String[] args) {
        new BestTimeToBuyAndSellStock2().testMaxProfit("[7,1,5,3,6,4]");
        new BestTimeToBuyAndSellStock2().testMaxProfit("[1,2,3,4,5]");
        new BestTimeToBuyAndSellStock2().testMaxProfit("[7,6,4,3,1]");
    }

    public void testMaxProfit(String text) {
        int[] prices = StringToArrayUtil.oneDimensionArr(text);
        System.out.println(maxProfit(prices));
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int income = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                income += (prices[i] - prices[i - 1]);
            }
        }
        return income;
    }
}
