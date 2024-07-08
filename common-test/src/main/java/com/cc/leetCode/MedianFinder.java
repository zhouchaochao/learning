package com.cc.leetCode;

import java.util.PriorityQueue;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc:   295. 数据流的中位数
 *         https://leetcode.cn/problems/find-median-from-data-stream/
 *         中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 *
 * 例如 arr = [2,3,4] 的中位数是 3
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 *
 * 输入
 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 * [[], [1], [2], [], [3], []]
 * 输出
 * [null, null, null, 1.5, null, 2.0]
 *
 *
 * @date: 2023-06-19
 */

public class MedianFinder {

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);    // arr = [1]
        medianFinder.addNum(2);    // arr = [1, 2]
        System.out.println(medianFinder.findMedian()); // 返回 1.5 ((1 + 2) / 2)
        medianFinder.addNum(3);    // arr[1, 2, 3]
        System.out.println(medianFinder.findMedian()); // return 2.0
    }

    /**
     *             root
     *          /       \
     *        /          \
     *  queLeftLess(多)  queRightBig
     *
     *  queRightBig.size + 1/0 = queLeftLess.size
     */

    // 一直保证 queMin.size 比 queMax.size 相等/或者多一个
    //左边，小于中位数，最大堆
    PriorityQueue<Integer> queLeftLess;
    //右边，大于中位数，最小堆
    PriorityQueue<Integer> queRightBig;

    public MedianFinder() {
        queLeftLess = new PriorityQueue<Integer>((a, b) -> (b - a));
        queRightBig = new PriorityQueue<Integer>((a, b) -> (a - b));
    }

    public void addNum(int num) {
        if (queLeftLess.isEmpty() || num <= queLeftLess.peek()) {
            queLeftLess.offer(num);
            // queMin 比 queMax 多2个，调整
            if (queRightBig.size() + 1 < queLeftLess.size()) {
                queRightBig.offer(queLeftLess.poll());
            }
        } else {
            queRightBig.offer(num);
            // queMax 比 queMax 多1个，调整
            if (queRightBig.size() > queLeftLess.size()) {
                queLeftLess.offer(queRightBig.poll());
            }
        }
    }

    public double findMedian() {
        if (queLeftLess.size() > queRightBig.size()) {
            return queLeftLess.peek();
        }
        return (queLeftLess.peek() + queRightBig.peek()) / 2.0;
    }
}
