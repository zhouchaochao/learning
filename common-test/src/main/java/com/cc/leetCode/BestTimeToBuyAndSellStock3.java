package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;

/**
 * Title: BestTimeToBuyAndSellStock3
 * Description: BestTimeToBuyAndSellStock3 你最多可以完成 两笔 交易。TODO 有动态规划方法
 * Date:  2019/7/24
 * 123. 买卖股票的最佳时机 III
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BestTimeToBuyAndSellStock3 {

    public static void main(String[] args) {
        new BestTimeToBuyAndSellStock3().testMaxProfit("[3,3,5,0,0,3,1,4]");
        new BestTimeToBuyAndSellStock3().testMaxProfit("[1,2,3,4,5]");
        new BestTimeToBuyAndSellStock3().testMaxProfit("[7,6,4,3,1]");
        new BestTimeToBuyAndSellStock3().testMaxProfit("[6,1,3,2,4,7]");
    }

    public void testMaxProfit(String text) {
        int[] prices = StringToArrayUtil.oneDimensionArr(text);
        System.out.println(maxProfit2(prices));
    }

    public int maxProfit(int[] prices) {
        int[] oneTransactionSolution = maxProfit(prices, 0, prices.length);
        int oneTransactionBuyIndex = oneTransactionSolution[0];
        int oneTransactionSellIndex = oneTransactionSolution[1];
        if (oneTransactionSolution[2] == 0) {//一次交易的最好结果都是0，没有继续的必要
            return 0;
        }
        //在上面的一次交易中间，能够产生的最大损失
        int maxLoseBetweenThisTransaction = maxLose(prices, oneTransactionBuyIndex, oneTransactionSellIndex + 1);
        int twoTransactionMaxProfit = oneTransactionSolution[2] - maxLoseBetweenThisTransaction;

        //在上面一次交易的左边进行一次交易
        int[] leftOneBuySolution = maxProfit(prices, 0, oneTransactionBuyIndex);
        if (oneTransactionSolution[2] + leftOneBuySolution[2] > twoTransactionMaxProfit) {
            twoTransactionMaxProfit = oneTransactionSolution[2] + leftOneBuySolution[2];
        }

        //在上面一次交易的右边进行一次交易
        int[] rightOneBuySolution = maxProfit(prices, oneTransactionSellIndex, prices.length);
        if (oneTransactionSolution[2] + rightOneBuySolution[2] > twoTransactionMaxProfit) {
            twoTransactionMaxProfit = oneTransactionSolution[2] + rightOneBuySolution[2];
        }
        return twoTransactionMaxProfit;
    }

    //参考别人解法
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        //假定最开始账户资产为0
        int firstBuyProfit = -prices[0];//第一次买股票时的最大收益
        int firstSellProfit = 0;//第一次卖股票时的最大收益
        int secondBuyProfit = -prices[0];//第二次买股票时的最大收益
        int secondSellProfit = 0;//第二次卖股票时的最大收益

        for (int i = 1; i < prices.length; i++) {
            if (firstBuyProfit < -prices[i]) {
                firstBuyProfit = -prices[i];
            }
            if (firstSellProfit < firstBuyProfit + prices[i]) {
                firstSellProfit = firstBuyProfit + prices[i];
            }
            if (secondBuyProfit < firstSellProfit - prices[i]) {
                secondBuyProfit = firstSellProfit - prices[i];
            }
            if (secondSellProfit < secondBuyProfit + prices[i]) {
                secondSellProfit = secondBuyProfit + prices[i];
            }
        }

        return secondSellProfit;
    }

    /**
     * [begin,end) 之间一笔交易的最大收益的解法，包含买卖的位置信息
     *
     * @param prices
     * @param begin  包含
     * @param end    不包含
     * @return 长度为3的数组，[0]表示buyIndex,[1]表示sellIndex [2]表示profit
     */
    public int[] maxProfit(int[] prices, int begin, int end) {
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
     * [begin,end) 之间一笔交易的最大损失
     *
     * @param prices
     * @param begin  包含
     * @param end    不包含
     * @return 返回损失
     */
    public int maxLose(int[] prices, int begin, int end) {
        if (prices == null || prices.length == 0 || begin >= end || begin < 0 || end > prices.length) {
            return 0;
        }
        int historyLose = 0;
        int currentBuyPrice = prices[begin];
        int currentSellPrice = prices[begin];

        for (int i = begin; i < end; i++) {
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
