package com.cc.leetCode;

import com.cc.leetCode.util.ListNodeUtil;
import com.cc.leetCode.util.StringToArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: ReverseNodesInKGroup
 * Description: ReverseNodesInKGroup
 * https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 * 25. k个一组翻转链表
 * Date:  2019/4/4
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ReverseNodesInKGroup {

    private static final Logger logger = LoggerFactory.getLogger(ReverseNodesInKGroup.class);

    public static void main(String[] args) {

        String inpu = "[1,2,3,4,5,6,7]";
        int[] arr = StringToArrayUtil.oneDimensionArr(inpu);
        ListNode listNode = new ListNode(arr);

        listNode = new ReverseNodesInKGroup().reverseKGroup(listNode, 1);
        ListNodeUtil.printListNode(listNode);
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode virtualHead = new ListNode(0);
        virtualHead.next = head;

        ListNode tail = reverseKGroup(head, k, virtualHead);
        while (tail != null) {
            tail = reverseKGroup(tail.next, k, tail);
        }

        return virtualHead.next;
    }

    /**
     * 定义：before,head .....tail,after
     * 翻转head-tail(包含head和tail)
     *
     * @param head
     * @param k
     * @param before 定义before.next = head tail.next=after
     * @return 返回tail
     */
    public ListNode reverseKGroup(ListNode head, int k, ListNode before) {
        ListNode[] arr = new ListNode[k];
        ListNode current = head;
        for (int i = 0; i < k; i++) {
            if (current != null) {
                arr[i] = current;
                current = current.next;
            } else {
                return null;
            }
        }

        ListNode after = current;

        before.next = arr[k - 1];
        for (int i = k - 1; i > 0; i--) {
            arr[i].next = arr[i - 1];
        }
        arr[0].next = after;
        return arr[0];
    }

}
