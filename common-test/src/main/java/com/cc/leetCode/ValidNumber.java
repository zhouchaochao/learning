package com.cc.leetCode;

/**
 * Title: ValidNumber
 * Description: ValidNumber
 * 65. 有效数字
 * https://leetcode-cn.com/problems/valid-number/
 * Date:  2019/8/1
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ValidNumber {

    public static void main(String[] args) {
        validNumberTest("0");
        validNumberTest("0.1");
        validNumberTest("abc");
        validNumberTest("1 a");
        validNumberTest("2e10");
        validNumberTest(" -90e3   ");
        validNumberTest(" 1e");
        validNumberTest("e3");
        validNumberTest(" 6e-1");
        validNumberTest(" 99e2.5 ");
        validNumberTest(" 53.5e93");
        validNumberTest(" --6 ");
        validNumberTest("-+3");
        validNumberTest("95a54e53");
        validNumberTest(".1");
        validNumberTest("1.");
        validNumberTest("-.1");
        validNumberTest(" 005047e+6");
        validNumberTest("46.e3");
        validNumberTest(".2e81");
        validNumberTest(".");
        validNumberTest("+e");
        validNumberTest("0..");
        validNumberTest("7e69e");
    }

    public static void validNumberTest(String s) {
        System.out.println("\"" + s + "\"->" + new ValidNumber().isNumber(s));
    }

    /**
     * 1.是否有e 有
     * 1.1 前半部分 +/- long,float，不能为空
     * 1.2 后半部分 +/- long，不能为空
     * <p>
     * 2.是否有e 无
     * 2.1 +/- long,float，不能为空
     *
     * @param s
     * @return
     */
    public boolean isNumber(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        s = s.trim();

        if (hasE(s)) {
            int index = s.indexOf("e");
            String beforeE = s.substring(0, index);//arr[0];
            String afterE = s.substring(index + 1);//arr[1];

            if (!isLongFloatWithToken(beforeE)) {
                return false;
            }

            if (!isLongWithToken(afterE)) {
                return false;
            }
            return true;

        } else {
            return isLongFloatWithToken(s);
        }
    }


    public boolean hasDot(String s) {
        return s.contains(".");
    }

    public boolean hasE(String s) {
        return s.contains("e");
    }

    public boolean beginWithToken(String s) {
        return s.startsWith("+") || s.startsWith("-");
    }

    public boolean isAllCharNumber(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    public boolean isLongFloatWithToken(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        if (beginWithToken(s)) {
            s = s.substring(1);
        }

        if (hasDot(s)) {
            return isFloat(s);
        } else {
            return isLong(s);
        }
    }

    public boolean isLongWithToken(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        if (beginWithToken(s)) {
            s = s.substring(1);
        }

        if (hasDot(s)) {
            return false;
        } else {
            return isLong(s);
        }
    }

    public boolean isFloat(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        String[] arr = s.split("\\.");
        if (arr.length < 1 || arr.length > 2) {
            return false;
        }
        int index = 0;
        if (s.contains(".")) {
            index = s.indexOf(".");
        } else {
            return false;
        }
        String beforeDot = s.substring(0, index);//arr.length == 2 ? arr[0] : "0";
        String afterDot = s.substring(index + 1);
        //arr.length == 2 ? arr[1] : "0";

        if (!beforeDot.isEmpty()) {
            if (!isLong(beforeDot)) {
                return false;
            }
        }

        return (afterDot.isEmpty() || isAllCharNumber(afterDot));
    }

    public boolean isLong(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        if (!isAllCharNumber(s)) {
            return false;
        }
        //不能0开头
        if (s.length() > 1 && s.startsWith("0")) {
            //return false;
        }
        return true;
    }
}
