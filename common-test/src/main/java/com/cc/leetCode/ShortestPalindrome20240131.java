package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: ShortestPalindrome
 * https://leetcode.cn/problems/shortest-palindrome/description/
 * 214. 最短回文串
 * 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
 * 示例 1：
 *
 * 输入：s = "aacecaaa"
 * 输出："aaacecaaa"
 * 示例 2：
 *
 * 输入：s = "abcd"
 * 输出："dcbabcd"
 *
 * @date: 2024-01-31
 */

public class ShortestPalindrome20240131 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShortestPalindrome20240131.class);

    /**
     *
     * https://leetcode.cn/problems/shortest-palindrome/solutions/392561/zui-duan-hui-wen-chuan-by-leetcode-solution/
     * 1.
     * Rabin-Karp 字符串哈希算法
     * 2.
     * KMP 算法
     *  KMP 用于解决字符串 s 中是否存在字符串 p，以及出现的位置，通过求 p 的 next 数组
     *
     * @param args
     */

    public static void main(String[] args) {
        String input = "aacecaaa";
        System.out.println(input + " -> " + new ShortestPalindrome20240131().shortestPalindrome(input));

        input = "abcd";
        System.out.println(input + " -> " + new ShortestPalindrome20240131().shortestPalindrome(input));

        input = "aabba";
        // abbabba 输出
        // abbaabba 预期
        System.out.println(input + " -> " + new ShortestPalindrome20240131().shortestPalindrome(input));

        input = "";
        System.out.println(input + " -> " + new ShortestPalindrome20240131().shortestPalindrome(input));
    }

    public String shortestPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        int palindromeMax = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            if (isPalindrome(s, i)) {
                palindromeMax = i;
                break;
            }
        }

        // 本身就是回文的
        if (palindromeMax == s.length() - 1) {
            return s;
        }

        // 回文部分之后的字符串是需要倒转添加到前面，才能让整体成为回文
        return reserveString(s.substring(palindromeMax + 1)) + s;
    }

    String reserveString(String s) {
        char[] arr = s.toCharArray();
        int begin = 0;
        int end = arr.length-1;
        while (begin < end) {
            char temp = arr[begin];
            arr[begin] = arr[end];
            arr[end] = temp;
            begin++;
            end--;
        }
        return new String(arr);
    }


    /**
     * 判断字符串s[0,end] 是否是回文
     *
     * @param s
     * @param end
     * @return
     */
    boolean isPalindrome(String s, int end) {
        // 奇数个数0,4
        if (end % 2 == 0) {
            int num = end / 2;
            //int middleIndex = end / 2;
            for (int i = 1; i < num + 1; i++) {
                if (s.charAt(num - i) != s.charAt(num + i)) {
                    return false;
                }
            }
            return true;
        } else {
            // 偶数个数0,3
            int num = end / 2 + 1;
            for (int i = 1; i < num + 1; i++) {
                if (s.charAt(num - i) != s.charAt(num + i - 1)) {
                    return false;
                }
            }
            return true;
        }
    }
}