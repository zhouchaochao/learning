package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: RegularExpressionMatching
 * Description: RegularExpressionMatching
 * https://leetcode-cn.com/problems/regular-expression-matching/
 * 10. 正则表达式匹配
 * Date:  2019/4/3
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class RegularExpressionMatching {

    private static final Logger logger = LoggerFactory.getLogger(RegularExpressionMatching.class);

    public static void main(String[] args) {
        String s = "aa";
        String p = "a";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "aa";
        p = "a*";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "ab";
        p = ".*";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "aab";
        p = "c*a*b";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "mississippi";
        p = "mis*is*p*.";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "a";
        p = "ab*";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "a";
        p = ".*..a*";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "aaaaaaaaaaaaab";
        p = "a*a*a*a*a*a*a*a*a*a*c";
        System.out.println(new RegularExpressionMatching().preProcess(p));
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "abb";
        p = "..";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));

        s = "abbabaaaaaaacaa";
        p = "a*.*b.a.*c*b*a*c*";
        System.out.println(s + "_" + p + " " +  new RegularExpressionMatching().isMatch(s,p));
    }

    //aaaabbcaaa

    //a 可以
    //.
    //*
    //ab
    //a.
    //a*
    //.*
    //..
    //.a

    public boolean isMatch(String s, String p) {
        return isMatchParten(s,preProcess(p));
    }

    public boolean isMatchParten(String s, String p) {
        boolean result = false;
        if (s.equals(p)) {
            result = true;
            System.out.println(s + "_" + p + " " + result);
            return true;
        }

        if (p.length() == 0) {
            if (s.length() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (p.length() == 1) {
            if (s.length() == 1) {
                if (p.charAt(0) == '.' || s.equals(p)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (p.length() == 2) {
            if (isWorld(p.charAt(0))) {
                if (p.charAt(1) == '*') {//a*
                    if (s.length() == 0 || oneCharStr(s, p.charAt(0))) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (p.charAt(1) == '.') {//a.
                    if (s.length() == 2 && s.charAt(0) == p.charAt(0)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {//aa
                    if (s.equals(p)) {
                        return true;
                    }else {
                        return false;
                    }
                }

            } else if (p.charAt(0) == '.') {
                if (isWorld(p.charAt(1))) {//.a
                    if (s.length() == 2 && s.charAt(1) == p.charAt(1)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (p.charAt(1) == '.') {//..
                    if (s.length() == 2) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (p.charAt(1) == '*') {//.*
                    return true;
                }
            } else {
                return false;
            }
        }


        //上面把p=0，1，2的情况都处理了
        if (s.length() == 0) {
            if (isWorld(p.charAt(0)) || p.charAt(0) == '.') {
                if (p.charAt(1) == '*') {
                    return isMatchParten(s, p.substring(2));
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        //加快速度,取巧
        /*if (isWorld(p.charAt(p.length() - 1))) {
            if (s.charAt(s.length() - 1) != p.charAt(p.length() - 1)) {
                return false;
            }
        }*/

        if (isWorld(p.charAt(0))) {
            if (isWorld(p.charAt(1))) {//p=aa
                if (p.charAt(0) == s.charAt(0)) {
                    return isMatchParten(s.length() > 1 ? s.substring(1) : "", p.substring(1));
                } else {
                    return false;
                }
            } else if (p.charAt(1) == '.') {//p=a.
                if (p.charAt(0) == s.charAt(0)) {
                    return isMatchParten(s.length() > 1 ? s.substring(1) : "", p.substring(1));
                } else {
                    return false;
                }
            } else {//p=a*
                if (s.charAt(0) != p.charAt(0)) {
                    return isMatchParten(s, p.substring(2));
                } else {//首字母相同
                    if (isMatchParten(s, p.substring(2))) {//p前面的a*忽略掉
                        return true;
                    }
                    if (isMatchParten(s.length() > 1 ? s.substring(1) : "", p.substring(2))) {
                        return true;
                    }
                    for (int i = 1; i < s.length(); i++) {
                        if (s.charAt(i) == p.charAt(0)) {
                            if (isMatchParten(s.substring(i), p.substring(2))) {
                                return true;
                            }
                        } else {
                            return isMatchParten(s.substring(i), p.substring(2));
                        }
                    }
                    //System.out.println("not here  " + s + "_" + p);
                    return isMatchParten("", p.substring(2));
                }
            }
        } else if (p.charAt(0) == '.'){
            if (isWorld(p.charAt(1))) {//p=.a
                return isMatchParten(s.length() > 1 ? s.substring(1) : "", p.substring(1));
            } else if (p.charAt(1) == '.') {//p=..
                return isMatchParten(s.substring(1), p.substring(1));
            } else {//p=.*
                if (isMatchParten(s, p.substring(2))) {//忽略p前面的.*
                    return true;
                }
                if (isMatchParten(s.length() > 1 ? s.substring(1) : "", p.substring(2))) {
                    return true;
                }
                for (int i = 1; i < s.length(); i++) {
                    if (isMatchParten(s.substring(i), p.substring(2))) {
                        return true;
                    }
                }
                //System.out.println("not here  " + s + "_" + p);
                return isMatchParten("",p.substring(2));
                //return false;
            }
        } else { //首字母 '*'
            return false;
        }
    }

    public boolean isWorld(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        return false;
    }

    public boolean oneCharStr(String s, char c) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }

    public String preProcess(String p) {
        if (p == null || p.length() < 4) {
            return p;
        }

        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*') {
                if ((i >= 1) && (i + 2 < p.length())) {
                    if (p.charAt(i + 2) == '*') {
                        if (p.charAt(i - 1) == p.charAt(i + 1)) {
                            return preProcess(p.substring(0,i-1) + p.substring(i+1));
                        }
                    }
                }
            }
        }
        return p;
    }

}
