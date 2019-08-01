package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: BestTimeToBuyAndSellStock4
 * Description: BestTimeToBuyAndSellStock4
 * 188. 买卖股票的最佳时机 IV
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/
 * Date:  2019/7/24
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BestTimeToBuyAndSellStock4 {

    public static void main(String[] args) {
        new BestTimeToBuyAndSellStock4().testMaxProfit("[2,4,1]", 2);
        new BestTimeToBuyAndSellStock4().testMaxProfit("[3,2,6,5,0,3]", 2);

        new BestTimeToBuyAndSellStock4().testMaxProfit("[3,3,5,0,0,3,1,4]", 2);
        new BestTimeToBuyAndSellStock4().testMaxProfit("[1,2,3,4,5]", 2);
        new BestTimeToBuyAndSellStock4().testMaxProfit("[7,6,4,3,1]", 2);
        new BestTimeToBuyAndSellStock4().testMaxProfit("[6,1,3,2,4,7]", 2);
        new BestTimeToBuyAndSellStock4().testMaxProfit("[1]", 1);
    }

    public void testMaxProfit(String text, int k) {
        int[] prices = StringToArrayUtil.oneDimensionArr(text);
        System.out.println(maxProfit(k, prices));
    }

    public int maxProfit2(int k, int[] prices) {

        if (prices == null || prices.length < 2 || k < 1) {
            return 0;
        }

        if (k > prices.length / 2) {
            k = prices.length / 2;
        }

        //dp[i][j][l] 表示在时间范围[i,prices.length),有j+1次卖出股票机会所能获得的最大利润,l=0表示当前没有持有股票，l=1表示当前持有股票
        //那么最后要求的结果就是dp[0][k-1][0]
        int[][][] dp = new int[prices.length + 1][k][2];

        for (int j = 0; j < k; j++) {
            //dp[prices.length - 1][j][0] = 0;//不持有股票
            dp[prices.length - 1][j][1] = prices[prices.length - 1];//持有股票
        }

        for (int i = prices.length - 2; i >= 0; i--) {
            for (int j = 0; j < k; j++) {
                //当前没有持有股票
                //买入/不买
                dp[i][j][0] = Math.max(-prices[i] + dp[i + 1][j][1], dp[i + 1][j][0]);

                //当前持有股票
                //卖出/不卖
                dp[i][j][1] = Math.max(prices[i] + (j == 0 ? 0 : dp[i + 1][j - 1][0]), dp[i + 1][j][1]);
            }
        }

        return dp[0][k - 1][0];
    }

    public int maxProfit(int k, int[] prices) {

        if (prices == null || prices.length < 2 || k < 1) {
            return 0;
        }

        List<Integer> list = prehandArr(prices);

        if (k > list.size() / 2) {
            int maxProfit = 0;
            for (int i = 1; i < list.size(); ) {
                if (list.get(i) > list.get(i - 1)) {
                    maxProfit += list.get(i) - list.get(i - 1);
                    i = i + 2;
                } else {
                    i++;
                }
            }
            return maxProfit;
        }

        //dp[i][j][l] 表示在时间范围[i,prices.length),有j+1次卖出股票机会所能获得的最大利润,l=0表示当前没有持有股票，l=1表示当前持有股票
        //那么最后要求的结果就是dp[0][k-1][0]
        int[][][] dp = new int[list.size() + 1][k][2];

        for (int j = 0; j < k; j++) {
            //dp[prices.length - 1][j][0] = 0;//不持有股票
            dp[list.size() - 1][j][1] = list.get(list.size() - 1);//持有股票
        }

        for (int i = list.size() - 2; i >= 0; i--) {
            for (int j = 0; j < k; j++) {
                //当前没有持有股票
                //买入/不买
                dp[i][j][0] = Math.max(-list.get(i) + dp[i + 1][j][1], dp[i + 1][j][0]);

                //当前持有股票
                //卖出/不卖
                dp[i][j][1] = Math.max(list.get(i) + (j == 0 ? 0 : dp[i + 1][j - 1][0]), dp[i + 1][j][1]);
            }
        }

        return dp[0][k - 1][0];
    }

    /**
     * 预处理，只保留极值
     *
     * @param prices
     * @return
     */
    public List<Integer> prehandArr(int[] prices) {
        List<Integer> list = new ArrayList<>(prices.length);
        list.add(prices[0]);
        for (int i = 1; i < prices.length - 1; i++) {
            if (prices[i] >= prices[i - 1] && prices[i] >= prices[i + 1]) {
                list.add(prices[i]);
            } else if (prices[i] <= prices[i - 1] && prices[i] <= prices[i + 1]) {
                list.add(prices[i]);
            }
        }
        list.add(prices[prices.length - 1]);
        return list;
    }
}
