package com.cc.leetCode;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: InsertInterval
 * Description: InsertInterval
 * 57. 插入区间
 * https://leetcode-cn.com/problems/insert-interval/
 * Date:  2019/4/16
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class InsertInterval {

    static class Interval {
        int start;
        int end;

        public Interval() {
            start = 0;
            end = 0;
        }

        public Interval(int[] arr) {
            start = arr[0];
            end = arr[1];
        }

        public Interval(int s, int e) {
            start = s;
            end = e;
        }

        public int[] toArr() {
            int[] arr = new int[2];
            arr[0] = start;
            arr[1] = end;
            return arr;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(InsertInterval.class);

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1,3));
        intervals.add(new Interval(6,9));
        Interval newInterval = new Interval(2,5);
        System.out.println(JSON.toJSONString(new InsertInterval().insert(intervals, newInterval)));

        //[[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
        intervals = new ArrayList<>();
        intervals.add(new Interval(1, 2));
        intervals.add(new Interval(3, 5));
        intervals.add(new Interval(6, 7));
        intervals.add(new Interval(8, 10));
        intervals.add(new Interval(12, 16));
        newInterval = new Interval(4, 8);
        System.out.println(JSON.toJSONString(new InsertInterval().insert(intervals, newInterval)));
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<Interval> originalData = new ArrayList<>(intervals.length);
        for (int i = 0; i < intervals.length; i++) {
            originalData.add(new Interval(intervals[i]));
        }
        Interval toBeInsert = new Interval(newInterval);
        List<Interval> intervalList = insert(originalData, toBeInsert);
        int[][] returnData = new int[intervalList.size()][];
        for (int i = 0; i < intervalList.size(); i++) {
            returnData[i] = intervalList.get(i).toArr();
        }
        return returnData;
    }

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> newList = new ArrayList<>();
        doInsert(intervals, newList, newInterval, 0);
        return newList;
    }

    public List<Interval> doInsert(List<Interval> intervals,List<Interval> newList, Interval newInterval,int index) {
        if (index > intervals.size() - 1) {//intervals 处理完了
            if (newInterval != null) {
                newList.add(newInterval);
            }
            return newList;
        }

        if (newInterval == null) {
            for (int i = index; i < intervals.size(); i++) {
                newList.add(intervals.get(i));
            }
            return newList;
        }

        Interval first = intervals.get(index);
        if (newInterval.start < first.start) {
            if (newInterval.end < first.start) {
                newList.add(newInterval);
                newInterval= null;
            } else if (newInterval.end <= first.end) {
                newInterval.end = first.end;
                index++;
            } else if (newInterval.end > first.end) {
                index++;
            }
        } else if (newInterval.start == first.start) {
            if (newInterval.end <= first.end) {
                newInterval = null;
            } else if (newInterval.end > first.end) {
                index++;
            }
        } else if (newInterval.start > first.start && newInterval.start < first.end) {
            if (newInterval.end <= first.end) {
                newInterval = null;
            } else if (newInterval.end > first.end) {
                index++;
                newInterval.start = first.start;
            }
        } else if (newInterval.start == first.end) {
            index++;
            newInterval.start = first.start;
        } else if (newInterval.start > first.end) {
            newList.add(first);
            index++;
        }
        return doInsert(intervals,newList,newInterval,index);
    }

}
