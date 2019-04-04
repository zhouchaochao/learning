package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: MergeKSortedLists
 * Description: MergeKSortedLists
 * 23. 合并K个排序链表
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 * Date:  2019/4/4
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */

public class MergeKSortedLists {

    private static final Logger logger = LoggerFactory.getLogger(MergeKSortedLists.class);

    public void printListNode(ListNode listNode) {
        String result = "";
        ListNode tmp = listNode;
        while (tmp != null) {
            result += tmp.val + "->";
            tmp = tmp.next;
        }
        System.out.println(result);
    }

    public static void main(String[] args) {

        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1);
        lists[0].next = new ListNode(4);
        lists[0].next.next = new ListNode(5);

        lists[1] = new ListNode(1);
        lists[1].next = new ListNode(3);
        lists[1].next.next = new ListNode(4);

        lists[2] = new ListNode(2);
        lists[2].next = new ListNode(6);

        ListNode result = new MergeKSortedLists().mergeKLists(lists);
        new MergeKSortedLists().printListNode(result);

        //输入[[-10,-9,-9,-9,-7,-2,-1,2,4],[-9,-7,-6,-6,-3,0,1,3],[-10,-9,-2,-1,1,3]]
        //输出[-10,-9,-9,-9,-9,-10,-9,-7,-7,-6,-6,-3,-2,-2,-1,-1,0,1,1,2,3,3,4]
        //预期结果[-10,-10,-9,-9,-9,-9,-9,-7,-7,-6,-6,-3,-2,-2,-1,-1,0,1,1,2,3,3,4]

        String input = "[[-10,-9,-9,-9,-7,-2,-1,2,4],[-9,-7,-6,-6,-3,0,1,3],[-10,-9,-2,-1,1,3]]";
        int[][] arr = StringToArrayUtil.twoDimensionArr(input);
        lists[0] = new ListNode(arr[0]);
        lists[1] = new ListNode(arr[1]);
        lists[2] = new ListNode(arr[2]);

        result = new MergeKSortedLists().mergeKLists(lists);
        new MergeKSortedLists().printListNode(result);

        lists = new ListNode[0];
        result = new MergeKSortedLists().mergeKLists(lists);
        new MergeKSortedLists().printListNode(result);

        input = "[[-10,-9,-6,-5],[-8,-5,-4,-3,-3,-3,0,2],[-9,-8,-3,3]]";
        arr = StringToArrayUtil.twoDimensionArr(input);
        lists = new ListNode[3];
        lists[0] = new ListNode(arr[0]);
        lists[1] = new ListNode(arr[1]);
        lists[2] = new ListNode(arr[2]);
        result = new MergeKSortedLists().mergeKLists(lists);
        new MergeKSortedLists().printListNode(result);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = null;
        ListNode node = null;
        int begin = sortArray(lists);

        while (begin < lists.length && lists[begin] != null) {
            if (node == null) {
                head = lists[begin];
                node = lists[begin];
            } else {
                node.next = lists[begin];
                node = node.next;
            }

            lists[begin] = lists[begin].next;
            if (lists[begin] == null) {
                begin++;
            } else {
                adjustSortedArray(lists, begin);
            }
        }
        return head;
    }

    /**
     * 除了index位置外，数组是有序的。
     * 并且index之前都是null
     *
     * @param lists
     * @param index
     * @return
     */
    public void adjustSortedArray(ListNode[] lists, int index) {
        for (int i = index; i < lists.length; i++) {
            if (i < lists.length - 1 && lists[i].val > lists[i + 1].val) {
                swap(lists, i, i + 1);
            } else {
                break;
            }
        }
    }

    /**
     * 快速排序
     *
     * @param lists
     * @return
     */
    public int sortArray(ListNode[] lists) {
        int notNullIndex = 0;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] == null) {
                swap(lists, i, notNullIndex);
                notNullIndex++;
            }
        }
        sortArray(lists, notNullIndex, lists.length - 1);
        return notNullIndex;
    }

    /**
     * 快速排序
     *
     * @param lists
     * @return
     */
    public void sortArray(ListNode[] lists, int x, int y) {

        if (x >= y) {
            return;
        }

        ListNode tag = lists[x];
        int left = x + 1;
        int right = y;

        while (left < right) {
            //找到left=right 或者left.val>tag
            while (lists[left].val <= tag.val && left < right) {
                left++;
            }
            //找到right.val<=tag
            while (lists[right].val > tag.val && left < right) {
                right--;
            }

            if (left < right) {
                swap(lists, left, right);
                left++;
                right--;//有可能造成left>right
            }
        }

        //以right为分隔符
        if (left == right) {
            if (lists[right].val < tag.val) {
                swap(lists, x, right);
                sortArray(lists, x, right - 1);
                sortArray(lists, right + 1, y);
            } else {
                sortArray(lists, x, right - 1);
                sortArray(lists, right, y);
            }
        } else if (left > right) {
            swap(lists, x, right);
            sortArray(lists, x, right - 1);
            sortArray(lists, right + 1, y);
        }
    }

    public void swap(ListNode[] lists, int x, int y) {
        ListNode tmp = lists[x];
        lists[x] = lists[y];
        lists[y] = tmp;
    }

}
