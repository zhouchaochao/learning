package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Title: WildcardMatching
 * Description: WildcardMatching
 * 44. 通配符匹配
 * https://leetcode-cn.com/problems/wildcard-matching/
 * Date:  2019/4/7
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class WildcardMatching {

    private static final Logger logger = LoggerFactory.getLogger(WildcardMatching.class);

    public static void main(String[] args) {

        String s = "aa";
        String p = "a";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "aa";
        p = "*";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "cb";
        p = "?a";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "adceb";
        p = "*a*b";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "acdcb";
        p = "a*c?b";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "a";
        p = "a*";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "leetcode";
        p = "*e*t?d*";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "c";
        p = "*?*";
        System.out.println(new WildcardMatching().isMatch(s,p));

        s = "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb";
        p ="**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb";
        long begin = System.currentTimeMillis();
        System.out.println(new WildcardMatching().isMatch(s,p) + " 耗时：" + (System.currentTimeMillis()-begin));

        s = "baaabbabbbaabbbbbbabbbaaabbaabbbbbaaaabbbbbabaaaaabbabbaabaaababaabaaabaaaabbabbbaabbbbbaababbbabaaabaabaaabbbaababaaabaaabaaaabbabaabbbabababbbbabbaaababbabbaabbaabbbbabaaabbababbabababbaabaabbaaabbba";
        p = "*b*ab*bb***abba*a**ab***b*aaa*a*b****a*b*bb**b**ab*ba**bb*bb*baab****bab*bbb**a*a*aab*b****b**ba**abba";
        begin = System.currentTimeMillis();
        System.out.println(new WildcardMatching().isMatch(s,p) + " 耗时：" + (System.currentTimeMillis()-begin));

        //System.out.println(Arrays.toString(new WildcardMatching().split(p)));
        //System.out.println(Arrays.toString(new WildcardMatching().split("dabc*bca*a")));
        //System.out.println(Arrays.toString(new WildcardMatching().split("abca*d***bcde")));

        int index = 0;
        if (p.length() > 10) {
            String maxWord = "aababaaaabb";
            while (index > -1) {
                index = s.indexOf(maxWord, index);
                if (index == -1) {
                    //System.out.println("不匹配");
                    return;
                }
                String s1 = s.substring(0, index);
                String s2 = index + maxWord.length() < s.length() ? s.substring(index + maxWord.length()) : "";
                //System.out.println(s1 + maxWord + s2);
                if (s.equals(s1 + maxWord + s2)) {
                    //System.out.println("s1:" + s1 + " s2:" + s2);
                } else {
                    //System.out.println("not here");
                }
                index = index + 1;
            }
            //System.out.println("不匹配");
        }
    }


    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        p = preProcess(p);
        return isStringMatch(s, p);
    }

    public boolean isStringMatch(String s, String p) {
        //比较最后一个字符
        if (!p.isEmpty() && isWorld(p.charAt(p.length() - 1))) {
            if (s.isEmpty()) {
                return false;
            } else {
                if (s.charAt(s.length() - 1) != p.charAt(p.length() - 1)) {//最后一个字母不相等
                    return false;
                }
            }
        }

        //如果p的长度比较长的话，先找到p中最长的连续字符，然后根据这个连续字符分隔s,然后分段进行匹配加快速度
        if (p.length() > 5) {
            String[] arr = split(p);
            String p1 = arr[0];
            String maxWord = arr[1];
            String p2 = arr[2];
            if (maxWord.length() >= 2) {
                int index = 0;
                while (index > -1) {
                    index = s.indexOf(maxWord, index);
                    if (index == -1) {
                        return false;
                    }
                    String s1 = s.substring(0, index);
                    String s2 = index + maxWord.length() < s.length() ? s.substring(index + maxWord.length()) : "";
                    if (isStringMatch(s1, p1) && isStringMatch(s2, p2)) {
                        return true;
                    }
                    index = index + 1;
                }
                return false;
            }
        }
        return isMatchMy(s,p);
    }

    public boolean isMatchMy(String s, String p) {
        if (s.equals(p)) {
            return true;
        }

        if (p.equals("*")) {
            return true;
        }

        if (s.isEmpty()) {
            if (p.isEmpty()) {
                return true;
            } else if (p.length() == 1) {
                if (p.equals("*")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (p.charAt(0) == '*') {
                    return isMatchMy("", p.substring(1));
                } else {
                    return false;
                }
            }
        } else if (s.length() >= 1) {
            if (p.length() == 0) {
                return false;
            } else if (p.length() == 1) {
                if (p.charAt(0) == '*') {
                    return true;
                } else if (p.charAt(0) == '?') {
                    return s.length() == 1;
                } else {
                    if (p.charAt(0) == s.charAt(0) && s.length() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else if (p.length() > 1) {
                if (p.charAt(0) == '*') {
                    if (isMatchMy("", p.substring(1))) {
                        return true;
                    }
                    for (int i = 0; i < s.length(); i++) {
                        if (isMatchMy(s.substring(i),p.substring(1))) {
                            return true;
                        }
                    }
                    return false;
                } else if (p.charAt(0) == '?') {
                    return isMatchMy(s.length() == 1 ? "" : s.substring(1), p.substring(1));
                } else {
                    if (p.charAt(0) == s.charAt(0)) {
                        return isMatchMy(s.length() == 1 ? "" : s.substring(1), p.substring(1));
                    } else {
                        return false;
                    }
                }
            }
        }

        return false;
    }


    public boolean isWorld(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        return false;
    }

    /**
     * 将p中连续的*合并为一个
     * @param p
     * @return
     */
    public String preProcess(String p) {
        if (p == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*') {
                if ((i >= 1) && p.charAt(i - 1) == '*') {
                    continue;
                }
            }
            sb.append(p.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 找到p中最长的连续字符，然后根据连续字符分隔
     * @param p
     * @return 返回长度为3的数组，arr[1]是p中最长的连续字符
     */
    public String[] split(String p) {
        String[] arr = new String[3];
        if (p == null) {
            return null;
        }
        String maxString = "";
        int maxStringIndex = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < p.length(); i++) {
            if (isWorld(p.charAt(i))) {
                sb.append(p.charAt(i));
            } else {//找到非字符
                if (sb.length()>maxString.length()) {
                    maxString = sb.toString();
                    maxStringIndex = i - maxString.length();
                }
                sb = new StringBuffer();
            }
        }

        if (sb.length() > maxString.length()) {
            maxString = sb.toString();
            maxStringIndex = p.length() - maxString.length();
        }

        arr[0] = p.substring(0, maxStringIndex);
        arr[1] = maxString;
        arr[2] = maxStringIndex + maxString.length() < p.length() ? p.substring(maxStringIndex + maxString.length()) : "";
        return arr;
    }

}
