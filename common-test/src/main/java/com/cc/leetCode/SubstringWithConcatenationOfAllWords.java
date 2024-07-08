package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: SubstringWithConcatenationOfAllWords
 * Description: SubstringWithConcatenationOfAllWords
 * https://leetcode-cn.com/problems/substring-with-concatenation-of-all-words/
 * 30. 串联所有单词的子串
 * Date:  2019/4/4
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class SubstringWithConcatenationOfAllWords {

    private static final Logger logger = LoggerFactory.getLogger(SubstringWithConcatenationOfAllWords.class);

    public static void main(String[] args) {
        String s = "barfoothefoobarman";
        String[] words = new String[]{"foo", "bar"};
        System.out.println(new SubstringWithConcatenationOfAllWords().findSubstring(s, words));

        s = "wordgoodgoodgoodbestword";
        words = new String[]{"word", "good", "best", "word"};
        System.out.println(new SubstringWithConcatenationOfAllWords().findSubstring(s, words));
    }

    public List<Integer> findSubstring2(String s, String[] words) {
        List<Integer> res = new ArrayList<Integer>();
        int m = words.length, n = words[0].length(), ls = s.length();
        for (int i = 0; i < n; i++) {
            if (i + m * n > ls) {
                break;
            }
            Map<String, Integer> differ = new HashMap<String, Integer>();
            for (int j = 0; j < m; j++) {
                String word = s.substring(i + j * n, i + (j + 1) * n);
                differ.put(word, differ.getOrDefault(word, 0) + 1);
            }
            for (String word : words) {
                differ.put(word, differ.getOrDefault(word, 0) - 1);
                if (differ.get(word) == 0) {
                    differ.remove(word);
                }
            }
            for (int start = i; start < ls - m * n + 1; start += n) {
                if (start != i) {
                    String word = s.substring(start + (m - 1) * n, start + m * n);
                    differ.put(word, differ.getOrDefault(word, 0) + 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                    word = s.substring(start - n, start);
                    differ.put(word, differ.getOrDefault(word, 0) - 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                }
                if (differ.isEmpty()) {
                    res.add(start);
                }
            }
        }
        return res;
    }

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new java.util.ArrayList<Integer>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return result;
        }

        String wordsStandardStr = arrToStandardStr(words);

        int wordsSum = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                wordsSum += words[i].charAt(j);
            }
        }

        int wordSize = words.length;
        int eachWordSize = words[0].length();
        int windowSize = wordSize * eachWordSize;
        int windowSum = 0;
        for (int i = 0; i < s.length(); i++) {
            windowSum += s.charAt(i);
            if (i < windowSize - 1) {
                continue;
            } else if (i >= windowSize) {
                windowSum -= s.charAt(i - windowSize);
            }
            if (windowSum == wordsSum) {
                if (isMath(s, i - windowSize + 1, wordsStandardStr, wordSize, eachWordSize)) {
                    result.add(i - windowSize + 1);
                }
            }
        }

        return result;
    }

    /**
     * 从s的 startIndex 开头的 wordSize*eachWordSize 个字符是否和 comparedTo 相等
     *
     * @param s
     * @param startIndex
     * @param comparedTo
     * @param wordSize
     * @param eachWordSize
     * @return
     */
    public boolean isMath(String s, int startIndex, String comparedTo, int wordSize, int eachWordSize) {
        String[] sToArr = new String[wordSize];
        for (int i = 0; i < wordSize; i++) {
            sToArr[i] = s.substring(startIndex + eachWordSize * i, startIndex + (i + 1) * eachWordSize);
        }
        String standardStr = arrToStandardStr(sToArr);
        return standardStr.equals(comparedTo);
    }

    public String arrToStandardStr(String[] arr) {
        java.util.Arrays.sort(arr);
        StringBuffer s = new StringBuffer(arr.length * arr[0].length());
        for (int i = 0; i < arr.length; i++) {
            s.append(arr[i]);
        }
        return s.toString();
    }
}
