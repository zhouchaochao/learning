package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;

/**
 * Title: BestTimeToBuyAndSellStock6
 * Description: BestTimeToBuyAndSellStock6
 * 714. 买卖股票的最佳时机含手续费
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
 * Date:  2019/7/24
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BestTimeToBuyAndSellStock6 {

    public static void main(String[] args) {
        new BestTimeToBuyAndSellStock6().testMaxProfit("[1,3,2,8,4,9]", 2);
    }

    public void testMaxProfit(String text, int fee) {
        int[] prices = StringToArrayUtil.oneDimensionArr(text);
        System.out.println(maxProfit(prices, fee));
    }

    //很好
    public int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int hasStockProfit = -prices[0] - fee;//当前有股票的最大收益
        int noStockProfit = 0;//当前没有股票的最大收益
        for (int i = 1; i < prices.length; i++) {
            hasStockProfit = Math.max(noStockProfit - prices[i] - fee, hasStockProfit);
            noStockProfit = Math.max(hasStockProfit + prices[i], noStockProfit);
        }

        return noStockProfit;
    }
}
