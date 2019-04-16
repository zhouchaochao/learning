package com.cc.leetCode.util;

import com.cc.leetCode.ListNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: ListNodeUtil
 * Description: ListNodeUtil
 * Date:  2019/4/4
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ListNodeUtil {

    public static void printListNode(ListNode listNode) {
        String result = "";
        ListNode tmp = listNode;
        while (tmp != null) {
            result += tmp.val + "->";
            tmp = tmp.next;
        }
        System.out.println(result);
    }

}
