package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;

import java.util.Arrays;

/**
 * Title: BestTimeToBuyAndSellStock
 * Description: BestTimeToBuyAndSellStock
 * 121. 买卖股票的最佳时机
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 * Date:  2019/7/24
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        new BestTimeToBuyAndSellStock().testMaxProfit("[7,1,5,3,6,4]");
        new BestTimeToBuyAndSellStock().testMaxProfit("[7,6,4,3,1]");

        new BestTimeToBuyAndSellStock().testMaxLost("[7,1,5,3,6,4]");
        new BestTimeToBuyAndSellStock().testMaxLost("[7,6,4,3,1]");
    }

    public void testMaxProfit(String text) {
        int[] prices = StringToArrayUtil.oneDimensionArr(text);
        System.out.println(maxProfit(prices));
    }

    public void testMaxLost(String text) {
        int[] prices = StringToArrayUtil.oneDimensionArr(text);
        System.out.println(maxLose(prices));
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int historyBestIncome = 0;
        int currentBuyPrice = prices[0];
        int currentSellPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < currentBuyPrice) {
                currentBuyPrice = prices[i];
                currentSellPrice = prices[i];
            } else {
                if (prices[i] > currentSellPrice) {
                    currentSellPrice = prices[i];
                    if (currentSellPrice - currentBuyPrice > historyBestIncome) {
                        historyBestIncome = currentSellPrice - currentBuyPrice;
                    }
                }
            }
        }
        return historyBestIncome;
    }

    //这种方法最简单
    public int maxProfit2(int[] prices) {
        if (prices.length <= 1)
            return 0;
        int min = prices[0], max = 0;
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return max;
    }

    /**
     * [begin,end) 之间一笔交易的最大收益的解法,包含买卖的位置信息
     *
     * @param prices
     * @param begin  包含
     * @param end    不包含
     * @return 长度为3的数组，[0]表示buyIndex,[1]表示sellIndex [2]表示profit
     */
    public int[] maxProfitContainIndex(int[] prices, int begin, int end) {
        int[] beginEndProfit = new int[3];
        if (prices == null || prices.length == 0 || begin >= end || begin < 0 || end > prices.length) {
            beginEndProfit[0] = begin;
            beginEndProfit[1] = begin;
            beginEndProfit[2] = 0;
            return beginEndProfit;
        }
        int historyBestBuyIndex = begin;
        int historyBestSellIndex = begin;
        int historyBestIncome = 0;
        int currentBuyIndex = begin;
        int currentSellIndex = begin;

        for (int i = begin + 1; i < end; i++) {
            if (prices[i] < prices[currentBuyIndex]) {
                currentBuyIndex = i;
                currentSellIndex = i;
            } else {
                if (prices[i] > prices[currentSellIndex]) {
                    currentSellIndex = i;
                    int currentIncome = prices[currentSellIndex] - prices[currentBuyIndex];
                    if (currentIncome > historyBestIncome) {
                        historyBestBuyIndex = currentBuyIndex;
                        historyBestSellIndex = currentSellIndex;
                        historyBestIncome = currentIncome;
                    }
                }
            }
        }

        beginEndProfit[0] = historyBestBuyIndex;
        beginEndProfit[1] = historyBestSellIndex;
        beginEndProfit[2] = historyBestIncome;
        return beginEndProfit;
    }

    /**
     * 买股票的最大损失
     *
     * @param prices
     * @return
     */
    public int maxLose(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int historyLose = 0;
        int currentBuyPrice = prices[0];
        int currentSellPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > currentBuyPrice) {
                currentBuyPrice = prices[i];
                currentSellPrice = prices[i];
            } else {
                if (prices[i] < currentSellPrice) {
                    currentSellPrice = prices[i];
                    if (currentSellPrice - currentBuyPrice < historyLose) {
                        historyLose = currentSellPrice - currentBuyPrice;
                    }
                }
            }
        }
        return historyLose;
    }
}
