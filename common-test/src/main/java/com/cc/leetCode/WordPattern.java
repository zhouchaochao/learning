package com.cc.leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: https://leetcode.cn/problems/word-pattern/?envType=study-plan-v2&envId=top-interview-150
 * 290. 单词规律
 * @date: 2024-04-03
 */

public class WordPattern {

    public boolean wordPattern(String pattern, String str) {
        Map<String, Character> str2ch = new HashMap<String, Character>();
        Map<Character, String> ch2str = new HashMap<Character, String>();
        int m = str.length();
        int i = 0;
        for (int p = 0; p < pattern.length(); ++p) {
            char ch = pattern.charAt(p);
            if (i >= m) {
                return false;
            }
            int j = i;
            while (j < m && str.charAt(j) != ' ') {
                j++;
            }
            String tmp = str.substring(i, j);
            if (str2ch.containsKey(tmp) && str2ch.get(tmp) != ch) {
                return false;
            }
            if (ch2str.containsKey(ch) && !tmp.equals(ch2str.get(ch))) {
                return false;
            }
            str2ch.put(tmp, ch);
            ch2str.put(ch, tmp);
            i = j + 1;
        }
        return i >= m;
    }
}
