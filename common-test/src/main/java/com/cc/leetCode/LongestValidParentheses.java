package com.cc.leetCode;

import java.util.Stack;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: LongestValidParentheses
 * https://leetcode.cn/problems/longest-valid-parentheses/
 * 32. 最长有效括号
 *
 * @date: 2023-07-03
 */

public class LongestValidParentheses {

    public static void main(String[] args) {
        String s = "(()";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));

        s = ")()())";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));

        s = ")()()))))))()()()";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));

        s = ")()()))))))(()()())";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));

        s = ")()()))))))(()()())))))))))))";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));

        s = ")()()))))))((()()())))))))))))";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));

        s = "()(()";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));

        s = "()(())";
        System.out.println(new LongestValidParentheses().longestValidParentheses(s));
    }

    /**
     * 官方解法：
     * 1.dp dp[i]=dp[i−1]+dp[i−dp[i−1]−2]+2
     * 2.栈
     * 3.不需要额外的空间，left, right2个计数
     */

    Integer leftParenthesis = -1;
    Integer rightParenthesis = -2;

    /**
     * 如果是 (
     *   直接入栈
     * 如果是 )
     *  先看栈顶元素
     *      栈空，入栈
     *      )不匹配，入栈
     *      (匹配，栈顶出栈
     *          再查看栈顶
     *              栈空 2入栈
     *              ) 2入栈
     *              ( 2入栈
     *              如果是数字x x+2入栈
     *      数字
     *          数字前面一个peek看下能否是(，
     *              还得再看下(前面是否有数字
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {

        int maxNum = 0;
        Stack<Integer> stack = new Stack<>();
        Character leftC = '(';
        //Character rightC = ')';
        for (char thisChar : s.toCharArray()) {
            if (leftC.equals(thisChar)) {
                // 当前是 (
                stack.push(leftParenthesis);
            } else {
                // 当前是 )
                if (stack.isEmpty()) {
                    stack.push(rightParenthesis);
                } else if (leftParenthesis.equals(stack.peek())) {
                    // 栈顶是 (，匹配
                    stack.pop();
                    int num = addNumToStack(stack, 2);
                    maxNum = Math.max(maxNum, num);
                } else if (rightParenthesis.equals(stack.peek())) {
                    // 栈顶是 )，不匹配
                    stack.push(rightParenthesis);
                } else {
                    // 栈顶是 数字
                    if (stack.size() > 1) {
                        int top = stack.pop();
                        // 数字前面是 (
                        if (leftParenthesis.equals(stack.peek())) {
                            stack.pop();
                            int num = addNumToStack(stack, top + 2);
                            maxNum = Math.max(maxNum, num);
                        } else {
                            // 数字前面是 )
                            stack.push(top);
                            stack.push(rightParenthesis);
                        }
                    } else {
                        stack.push(rightParenthesis);
                    }
                }
            }
        }
        return maxNum;
    }

    private Integer addNumToStack(Stack<Integer> stack, Integer num) {
        if (stack.isEmpty()) {
            stack.push(num);
            return num;
        }
        // 前面是数字
        if (!leftParenthesis.equals(stack.peek()) && !rightParenthesis.equals(stack.peek())) {
            num += stack.pop();
            return addNumToStack(stack,  num);
        } else {
            // 前面是 ) （
            stack.push(num);
        }
        return num;
    }
}
