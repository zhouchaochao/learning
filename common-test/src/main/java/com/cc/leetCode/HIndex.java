package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 274. H 指数
 * https://leetcode.cn/problems/h-index/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-03-28
 */

public class HIndex {

    public int hIndex(int[] citations) {
        int n = citations.length, tot = 0;
        int[] counter = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (citations[i] >= n) {
                counter[n]++;
            } else {
                counter[citations[i]]++;
            }
        }
        for (int i = n; i >= 0; i--) {
            tot += counter[i];
            if (tot >= i) {
                return i;
            }
        }
        return 0;
    }

}
