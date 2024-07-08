package com.cc.leetCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 151. 反转字符串中的单词
 * https://leetcode.cn/problems/reverse-words-in-a-string/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-03-29
 */

public class ReverseWordsInAString {

    public String reverseWords(String s) {
        // 除去开头和末尾的空白字符
        s = s.trim();
        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ", wordList);
    }
}
