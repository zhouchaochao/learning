package com.cc.leetCode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 502. IPO
 * https://leetcode.cn/problems/ipo/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-16
 */

public class IPO {

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        int curr = 0;
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; ++i) {
            arr[i][0] = capital[i];
            arr[i][1] = profits[i];
        }
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);

        PriorityQueue<Integer> pq = new PriorityQueue<>((x, y) -> y - x);
        for (int i = 0; i < k; ++i) {
            while (curr < n && arr[curr][0] <= w) {
                pq.add(arr[curr][1]);
                curr++;
            }
            if (!pq.isEmpty()) {
                w += pq.poll();
            } else {
                break;
            }
        }

        return w;
    }
}
