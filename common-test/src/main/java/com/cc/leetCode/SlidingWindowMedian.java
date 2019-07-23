package com.cc.leetCode;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Title: SlidingWindowMedian
 * Description: SlidingWindowMedian 480. 滑动窗口中位数
 * https://leetcode-cn.com/problems/sliding-window-median/
 * <p>
 * Date:  2019/7/23
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class SlidingWindowMedian {

    public static void main(String[] args) {

        String text = "[2147483647,2147483647]";
        int k =2;
        new SlidingWindowMedian().test(text, k);

        text = "[1,3,-1,-3,5,3,6,7]";
        k =3;
        new SlidingWindowMedian().test(text, k);
    }

    public void test(String text,int k) {
        List<Integer> list = JSON.parseArray(text, Integer.class);
        int[] nums = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            nums[i] = list.get(i);
        }
        double[] result = new SlidingWindowMedian().medianSlidingWindow(nums, k);
        System.out.println(Arrays.toString(result));
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        List<Integer> windowList = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            insertValueToSortedList(windowList, nums[i]);
            //System.out.println(windowList);
        }
        double[] result = new double[nums.length - k + 1];
        double median = getMedian(windowList);
        result[0] = median;
        for (int i = k; i < nums.length; i++) {
            removeValueFromSortedList(windowList, nums[i - k]);
            insertValueToSortedList(windowList, nums[i]);
            median = getMedian(windowList);
            result[i - k + 1] = median;
        }
        return result;
    }

    /**
     * 获取中位数
     *
     * @param nums
     * @return
     */
    private double getMedian(List<Integer> nums) {
        if (nums.size() % 2 == 0) {
            return ((long)nums.get(nums.size() / 2 - 1) + nums.get(nums.size() / 2)) / 2.0;
        } else {
            return nums.get(nums.size() / 2);
        }
    }

    private void insertValueToSortedList(List<Integer> sortedList, int newValue) {
        //sortedList.add(newValue);
        //Collections.sort(sortedList);
        //System.out.println(sortedList);
        int bigOrEqualIndex = findFirstBigOrEqualIndex(sortedList, 0, sortedList.size() - 1, newValue);
        sortedList.add(bigOrEqualIndex, newValue);
    }

    private void removeValueFromSortedList(List<Integer> sortedList, int deleteValue) {
        //sortedList.remove(new Integer(deleteValue));

        int bigOrEqualIndex = findFirstBigOrEqualIndex(sortedList, 0, sortedList.size() - 1, deleteValue);
        sortedList.remove(bigOrEqualIndex);
    }

    /**
     * 找到 sortedList 中从begin 到 end 第一个 >=item 的位置，如果没有，返回end+1
     *
     * @param sortedList
     * @param begin
     * @param end
     * @param item
     * @return
     */
    private int findFirstBigOrEqualIndex(List<Integer> sortedList, int begin, int end, int item) {
        if (sortedList.size() == 0) {
            return 0;
        }
        if (begin == end) {
            if (sortedList.get(end) < item) {
                return end + 1;
            }
            return begin;
        }
        int middleIndex = (begin + end) / 2;
        if (sortedList.get(middleIndex) < item) {
            return findFirstBigOrEqualIndex(sortedList, middleIndex + 1, end, item);
        } else {
            return findFirstBigOrEqualIndex(sortedList, begin, middleIndex, item);
        }
    }
}
