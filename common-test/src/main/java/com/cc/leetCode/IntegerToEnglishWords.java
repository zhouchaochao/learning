package com.cc.leetCode;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: IntegerToEnglishWords
 *         https://leetcode.cn/problems/integer-to-english-words/
 *         273. 整数转换英文表示
 *         输入：num = 1234567
 *         输出："One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 *         0 <= num <= 231 - 1
 *         2的32次方是 = 4294967296 = 42.9亿
 * @date: 2023-06-19
 */

public class IntegerToEnglishWords {

    public static void main(String[] args) {
        int num = 0;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 3;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 10;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 11;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 12;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 20;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 21;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 22;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 30;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 31;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 32;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 100;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 101;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 102;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 110;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 111;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 112;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 120;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 121;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 122;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1000;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1001;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1002;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1010;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1011;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1012;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1020;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1021;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 2021;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 210021;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1000000;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 3210021;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1234567;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1000000000;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1000000001;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1000000010;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1000000011;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1000000021;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
        num = 1234567890;
        System.out.println(num + " -> " + new IntegerToEnglishWords().numberToWords(num));
    }

    /**
     * //trillion 万亿
     * Billion 10亿
     * Million 100W
     * Thousand 千
     * Hundred 百
     * 20-90: Twenty, Thirty, Forty, Fifty, Sixty, Seventy, Eighty, Ninety
     * 11-19: Eleven、Twelve、Thirteen、Fourteen、Fifteen、Sixteen、Seventeen、Eighteen、Nineteen
     * 1-10:  One、Two、Three、Four、Five、Six、Seven、Eight、Nine、Ten
     */
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        return join(getWordsOfUnit(num, 1000000000), getWordsOfUnit(num, 1000000), getWordsOfUnit(num, 1000), getWordsOfUnit(num, 1));
    }

    /**
     * 获取 exp 位上的字符串
     * @param num
     * @param exp 1000000000/1000000/1000/1
     * @return
     */
    public String getWordsOfUnit(int num, int exp) {
        int value999 = (num / exp) % 1000;
        if (value999 > 0) {
            return join(get999(value999), getUnit(exp));
        } else {
            return "";
        }
    }

    /**
     * 获取单位
     * @param exp
     * @return
     */
    private String getUnit(int exp) {
        if (exp - 1000 == 0) {
            return "Thousand";
        } else if (exp - 1000000 == 0) {
            return "Million";
        } else if (exp - 1000000000 == 0) {
            return "Billion";
        }
        return "";
    }

    public String get999(int value999) {
        if (value999 == 0) {
            return "";
        }
        String value = "";
        if (value999/100 != 0) {
            value = join(getOne(value999/100) ,"Hundred");
        }
        return join(value, get99(value999%100));
    }

    public String get99(int value99) {
        if (value99 == 0) {
            return "";
        }
        if (value99 < 10) {
            return getOne(value99);
        }
        if (value99 >= 10 && value99 <= 19) {
            return getElevent(value99);
        }
        return join(getTwenty(value99/10), getOne(value99 % 10));
    }

    private String getElevent(int low2Value) {
        if (low2Value == 10) {
            return "Ten";
        } else if (low2Value == 11) {
            return "Eleven";
        } else if (low2Value == 12) {
            return "Twelve";
        } else if (low2Value == 13) {
            return "Thirteen";
        } else if (low2Value == 14) {
            return "Fourteen";
        } else if (low2Value == 15) {
            return "Fifteen";
        } else if (low2Value == 16) {
            return "Sixteen";
        } else if (low2Value == 17) {
            return "Seventeen";
        } else if (low2Value == 18) {
            return "Eighteen";
        } else if (low2Value == 19) {
            return "Nineteen";
        }
        return "";
    }

    private String getTwenty(int tenValue) {
        if (tenValue == 2) {
            return "Twenty";
        } else if (tenValue == 3) {
            return "Thirty";
        }  else if (tenValue == 4) {
            return "Forty";
        }  else if (tenValue == 5) {
            return "Fifty";
        }  else if (tenValue == 6) {
            return "Sixty";
        }  else if (tenValue == 7) {
            return "Seventy";
        }  else if (tenValue == 8) {
            return "Eighty";
        }  else if (tenValue == 9) {
            return "Ninety";
        }
        return "";
    }

    private String getOne(int oneValue) {
        if (oneValue == 0) {
            return "";
        }else if (oneValue == 1) {
            return "One";
        } else if (oneValue == 2) {
            return "Two";
        } else if (oneValue == 3) {
            return "Three";
        } else if (oneValue == 4) {
            return "Four";
        } else if (oneValue == 5) {
            return "Five";
        } else if (oneValue == 6) {
            return "Six";
        } else if (oneValue == 7) {
            return "Seven";
        } else if (oneValue == 8) {
            return "Eight";
        } else if (oneValue == 9) {
            return "Nine";
        }
        return "";
    }

    private String join(String... arr) {
        return String.join(" ", Arrays.stream(arr).filter(s -> !"".equals(s)).collect(Collectors.toList()));
    }
}
