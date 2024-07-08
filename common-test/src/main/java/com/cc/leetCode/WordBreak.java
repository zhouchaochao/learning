package com.cc.leetCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 139. 单词拆分
 * https://leetcode.cn/problems/word-break/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-22
 */

public class WordBreak {

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

}
