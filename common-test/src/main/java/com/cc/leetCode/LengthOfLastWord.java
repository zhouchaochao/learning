package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 58. 最后一个单词的长度
 * https://leetcode.cn/problems/length-of-last-word/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-03-29
 */

public class LengthOfLastWord {
    public int lengthOfLastWord(String s) {
        int end = s.length() - 1;
        while(end >= 0 && s.charAt(end) == ' ') end--;
        if(end < 0) return 0;
        int start = end;
        while(start >= 0 && s.charAt(start) != ' ') start--;
        return end - start;
    }
}
