package com.cc.leetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 71. 简化路径
 * https://leetcode.cn/problems/simplify-path/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-03
 */

public class SimplifyPath {

    public String simplifyPath(String path) {
        String[] names = path.split("/");
        Deque<String> stack = new ArrayDeque<String>();
        for (String name : names) {
            if ("..".equals(name)) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else if (name.length() > 0 && !".".equals(name)) {
                stack.offerLast(name);
            }
        }
        StringBuffer ans = new StringBuffer();
        if (stack.isEmpty()) {
            ans.append('/');
        } else {
            while (!stack.isEmpty()) {
                ans.append('/');
                ans.append(stack.pollFirst());
            }
        }
        return ans.toString();
    }
}
