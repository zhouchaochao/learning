package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: BestTimeToBuyAndSellStock5
 * Description: BestTimeToBuyAndSellStock5
 * 309. 最佳买卖股票时机含冷冻期
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 * Date:  2019/7/24
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BestTimeToBuyAndSellStock5 {

    public static void main(String[] args) {
        new BestTimeToBuyAndSellStock5().testMaxProfit("[1,2,3,0,2]");
    }

    public void testMaxProfit(String text) {
        int[] prices = StringToArrayUtil.oneDimensionArr(text);
        System.out.println(maxProfit(prices));
    }

    public int maxProfit2(int[] prices) {
        int k = (prices.length + 1) / 3;
        if (prices == null || prices.length < 2 || k < 1) {
            return 0;
        }

        //List<Integer> list = prehandArr(prices);

        if (k > prices.length / 2) {
            k = prices.length / 2;
        }

        //dp[i][j][l] 表示在时间范围[i,prices.length),有j+1次卖出股票机会所能获得的最大利润,l=0表示当前没有持有股票，l=1表示当前持有股票,l=2表示锁定
        //那么最后要求的结果就是 dp[0][k-1][0]
        int[][][] dp = new int[prices.length + 1][k][3];

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
                dp[i][j][1] = Math.max(prices[i] + (j == 0 ? 0 : dp[i + 1][j - 1][2]), dp[i + 1][j][1]);

                //当前冷冻
                //不能买/卖
                dp[i][j][2] = dp[i + 1][j][0];
            }
        }

        return dp[0][k - 1][0];
    }


    public int maxProfit3(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        //当前所处的状态
        //从 [i,price.length) 所能取得的最大收益
        //初始化为 price.length-1 位置的值
        int hasStack = prices[prices.length - 1];//当前有股票状态，进行各种操作所能获得的最大收益
        int noStack = 0;//
        int cool = 0;//当前是冷冻状态，所能取得的最大收益

        for (int i = prices.length - 2; i >= 0; i--) {
            int nextNoStack = noStack;//第i+1没有股票时的收益
            noStack = Math.max(noStack, hasStack - prices[i]);//不买/买
            hasStack = Math.max(hasStack, cool + prices[i]);//不卖/卖
            cool = nextNoStack;//等于 i+1没有股票时的收益
        }

        return noStack;
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        //动态规划， 两个状态，第i天持有股票和第i天不持有股票，递推公式分别是： D[i][1] = max( d[i-1][1] , d[i-2][0] - prices[i]) D[i][0] = max(d[i-1][0], d[i-1][1] + prices[i]) 这里出过问题的是持有股票第二项，如果第i天是买入， 前一天一定是不持有且不能卖出，所以是大前天不持有的最大利润减去今天买入的价格

        //当前所处的状态
        //从 (-1,i] 所能取得的最大收益
        //初始化为 -1, 情况的值
        int hasStack = Integer.MIN_VALUE;//不可能。当前有股票状态，进行各种操作所能获得的最大收益
        int noStack = 0;//
        int previousNoStack = 0;//记录当前的前一个位置的noStack值

        for (int i = 0; i < prices.length; i++) {
            int tmp = noStack;//第 i-1 没有股票时的收益
            noStack = Math.max(noStack, hasStack + prices[i]);//继续/卖
            hasStack = Math.max(hasStack, previousNoStack - prices[i]);//继续/买  这里买是从i-2的noStack为基准的
            previousNoStack = tmp;//等于 i+1没有股票时的收益
        }

        return noStack;
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
