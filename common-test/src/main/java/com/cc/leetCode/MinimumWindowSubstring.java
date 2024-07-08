package com.cc.leetCode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Title: MinimumWindowSubstring
 * Description: MinimumWindowSubstring
 * 76. 最小覆盖子串
 * https://leetcode-cn.com/problems/minimum-window-substring/
 * Date:  2019/8/1
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class MinimumWindowSubstring {

    public static void main(String[] args) {
        minWindowTest("ADOBECODEBANC", "ABC");
        minWindowTest("caeacca", "aca");
    }

    Map<Character, Integer> ori = new HashMap<Character, Integer>();
    Map<Character, Integer> cnt = new HashMap<Character, Integer>();

    public String minWindow2(String s, String t) {
        int tLen = t.length();
        for (int i = 0; i < tLen; i++) {
            char c = t.charAt(i);
            ori.put(c, ori.getOrDefault(c, 0) + 1);
        }
        int l = 0, r = -1;
        int len = Integer.MAX_VALUE, ansL = -1, ansR = -1;
        int sLen = s.length();
        while (r < sLen) {
            ++r;
            if (r < sLen && ori.containsKey(s.charAt(r))) {
                cnt.put(s.charAt(r), cnt.getOrDefault(s.charAt(r), 0) + 1);
            }
            while (check() && l <= r) {
                if (r - l + 1 < len) {
                    len = r - l + 1;
                    ansL = l;
                    ansR = l + len;
                }
                if (ori.containsKey(s.charAt(l))) {
                    cnt.put(s.charAt(l), cnt.getOrDefault(s.charAt(l), 0) - 1);
                }
                ++l;
            }
        }
        return ansL == -1 ? "" : s.substring(ansL, ansR);
    }

    public boolean check() {
        Iterator iter = ori.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Character key = (Character) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (cnt.getOrDefault(key, 0) < val) {
                return false;
            }
        }
        return true;
    }


    public static void minWindowTest(String s, String t) {
        System.out.println(new MinimumWindowSubstring().minWindow(s, t));
    }
/*
    public String minWindow(String s, String t) {
        if (t == null || t.isEmpty()) {
            return "";
        }
        if (s == null || s.isEmpty()) {
            return "";
        }

        if (s.length() < t.length()) {
            return "";
        }

        if (s.contains(t)) {
            return t;
        }

        Map<Character, int[]> charCountFoundNeedMap = new HashMap<>(t.length());
        for (char c : t.toCharArray()) {
            int[] arr = charCountFoundNeedMap.getOrDefault(c, new int[2]);
            arr[1] = arr[1] + 1;
            charCountFoundNeedMap.put(c, arr);
        }
        String find = minWindow(s, 0, 0, null, t.length(), charCountFoundNeedMap);
        return find == null ? "" : find;
    }

    */

    /**
     * @param s
     * @param begin                 包含
     * @param end                   不包含
     * @param shortestStr           [begin,end)之间找到的符合要求的最短字符串
     * @param charMissingCount      与t相比，还缺少几个char
     * @param charCountFoundNeedMap 找到的char-count的全局映射关系,arr[0]表示found,arr[1]表示need
     * @return
     *//*
    public String minWindow(String s, int begin, int end, String shortestStr, Integer charMissingCount, Map<Character, int[]> charCountFoundNeedMap) {

        if (charMissingCount == 0) {//找了了全部t中的char,当前[begin,end)包含了t,符合要求，接下来移动begin
            if (shortestStr == null) {
                shortestStr = s.substring(begin, end);
            } else {
                String newFound = s.substring(begin, end);
                shortestStr = shortestStr.length() > newFound.length() ? newFound : shortestStr;
            }

            if (begin > s.length() - 1) {
                return shortestStr;
            }

            Character beginChar = s.charAt(begin);
            int[] findNeedCount = charCountFoundNeedMap.get(beginChar);
            //Integer thisCharCountNeed = charCountInTMap.get(beginChar);
            if (findNeedCount != null) {//是t中的字符
                if (findNeedCount[0] <= findNeedCount[1]) {//要减去一个
                    charMissingCount++;
                }
                findNeedCount[0] = findNeedCount[0] - 1;
                charCountFoundNeedMap.put(beginChar, findNeedCount);
            }
            return minWindow(s, begin + 1, end, shortestStr, charMissingCount, charCountFoundNeedMap);

        } else {//当前[begin,end)还不包含全部t中字符，接下来移动end

            if (end > s.length() - 1) {
                return shortestStr;
            }

            Character endChar = s.charAt(end);
            int[] findNeedCount = charCountFoundNeedMap.get(endChar);

            if (findNeedCount != null) {//是t中的字符
                if (findNeedCount[0] < findNeedCount[1]) {//由0变成了1
                    charMissingCount--;
                }
                findNeedCount[0] = findNeedCount[0] + 1;

                charCountFoundNeedMap.put(endChar, findNeedCount);
            }
            return minWindow(s, begin, end + 1, shortestStr, charMissingCount, charCountFoundNeedMap);
        }
    }
    */


    public String minWindow(String s, String t) {
        if (t == null || t.isEmpty()) {
            return "";
        }
        if (s == null || s.isEmpty()) {
            return "";
        }

        if (s.length() < t.length()) {
            return "";
        }

        if (s.contains(t)) {
            return t;
        }

        int[] charCountMissingArr = new int[128];
        for (char c : t.toCharArray()) {
            charCountMissingArr[c]++;
        }
        for (int i = 0; i < charCountMissingArr.length; i++) {
            if (charCountMissingArr[i] == 0) {
                charCountMissingArr[i] = s.length() + 1;
            }
        }
        String find = minWindow(s, 0, 0, null, t.length(), charCountMissingArr);
        return find == null ? "" : find;
    }

    /**
     * @param s
     * @param begin               包含
     * @param end                 不包含
     * @param shortestStr         [begin,end)之间找到的符合要求的最短字符串
     * @param charMissingCount    与t相比，还缺少几个char
     * @param charCountMissingArr 缺失的字符计数
     * @return
     */
    public String minWindow(String s, int begin, int end, String shortestStr, Integer charMissingCount, int[] charCountMissingArr) {

        if (charMissingCount == 0) {//找了了全部t中的char,当前[begin,end)包含了t,符合要求，接下来移动begin
            if (shortestStr == null) {
                shortestStr = s.substring(begin, end);
            } else {
                String newFound = s.substring(begin, end);
                shortestStr = shortestStr.length() > newFound.length() ? newFound : shortestStr;
            }

            if (begin > s.length() - 1) {
                return shortestStr;
            }

            Character beginChar = s.charAt(begin);
            if (charCountMissingArr[beginChar] != s.length() + 1) {
                charCountMissingArr[beginChar]++;//减去该字符
                if (charCountMissingArr[beginChar] > 0) {//
                    charMissingCount++;
                }
            }


            return minWindow(s, begin + 1, end, shortestStr, charMissingCount, charCountMissingArr);

        } else {//当前[begin,end)还不包含全部t中字符，接下来移动end

            if (end > s.length() - 1) {
                return shortestStr;
            }

            Character endChar = s.charAt(end);
            if (charCountMissingArr[endChar] != s.length() + 1) {//是t中的字符
                charCountMissingArr[endChar]--;//加上该字符
                if (charCountMissingArr[endChar] >= 0) {//该字符不缺失
                    charMissingCount--;
                }
            }
            return minWindow(s, begin, end + 1, shortestStr, charMissingCount, charCountMissingArr);
        }
    }
}
