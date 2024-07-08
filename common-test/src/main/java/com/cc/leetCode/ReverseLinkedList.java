package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 206. 反转链表
 * https://leetcode.cn/problems/reverse-linked-list/?envType=study-plan-v2&envId=top-100-liked
 * @date: 2024-06-03
 */

public class ReverseLinkedList {

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

}
