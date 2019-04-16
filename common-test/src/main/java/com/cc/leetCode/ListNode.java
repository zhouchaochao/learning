package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: ListNode
 * Description: ListNode
 * Date:  2019/4/4
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ListNode {

    public int val;
    public ListNode next;

    ListNode(int x) {
        val = x;
    }

    ListNode(int[] x) {
        if (x != null) {
            ListNode current = this;
            for (int i = 0; i < x.length; i++) {
                if (i == 0) {
                    val = x[i];
                } else {
                    current.next = new ListNode(x[i]);
                    current = current.next;
                }
            }
        }
    }
}
