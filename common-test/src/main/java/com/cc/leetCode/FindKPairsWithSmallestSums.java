package com.cc.leetCode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 373. 查找和最小的 K 对数字
 * https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-16
 */

public class FindKPairsWithSmallestSums {


    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (o1, o2)->{
            return nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]];
        });
        List<List<Integer>> ans = new ArrayList<>();
        int m = nums1.length;
        int n = nums2.length;
        for (int i = 0; i < Math.min(m, k); i++) {
            pq.offer(new int[]{i,0});
        }
        while (k-- > 0 && !pq.isEmpty()) {
            int[] idxPair = pq.poll();
            List<Integer> list = new ArrayList<>();
            list.add(nums1[idxPair[0]]);
            list.add(nums2[idxPair[1]]);
            ans.add(list);
            if (idxPair[1] + 1 < n) {
                pq.offer(new int[]{idxPair[0], idxPair[1] + 1});
            }
        }

        return ans;
    }

}
