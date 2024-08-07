package com.cc.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 22. 括号生成
 * https://leetcode.cn/problems/generate-parentheses/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-12
 */

public class GenerateParentheses {

    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<String>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }
        if (open < max) {
            cur.append('(');
            backtrack(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (close < open) {
            cur.append(')');
            backtrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }

}
