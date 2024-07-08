package com.cc.leetCode;

import java.util.PriorityQueue;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 295. 数据流的中位数
 * https://leetcode.cn/problems/find-median-from-data-stream/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-16
 */

public class FindMedianFromDataStream {

    PriorityQueue<Integer> queMin;
    PriorityQueue<Integer> queMax;

    public FindMedianFromDataStream() {
        queMin = new PriorityQueue<Integer>((a, b) -> (b - a));
        queMax = new PriorityQueue<Integer>((a, b) -> (a - b));
    }

    public void addNum(int num) {
        if (queMin.isEmpty() || num <= queMin.peek()) {
            queMin.offer(num);
            if (queMax.size() + 1 < queMin.size()) {
                queMax.offer(queMin.poll());
            }
        } else {
            queMax.offer(num);
            if (queMax.size() > queMin.size()) {
                queMin.offer(queMax.poll());
            }
        }
    }

    public double findMedian() {
        if (queMin.size() > queMax.size()) {
            return queMin.peek();
        }
        return (queMin.peek() + queMax.peek()) / 2.0;
    }

}
