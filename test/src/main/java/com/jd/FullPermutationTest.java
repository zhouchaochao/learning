package com.jd;

/**
 * Title: FullPermutationTest
 * Description: FullPermutationTest 全排列,假设输入的字符串中字符不重复
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/1/20
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class FullPermutationTest {


    public static void main(String[] args) {
        String str = "abcd";
        //System.out.println("count:" + fullPermutation(str));
        System.out.println("count:" + fullPermutation2(str));
    }

    private static int fullPermutation(String str) {
        if (str != null && !str.isEmpty()) {
            return fullPermutation("", str);
        }
        return 0;
    }

    //head不变，str全排列
    private static int fullPermutation(String head, String str) {
        if (str == null || str.isEmpty()) {
            System.out.println(head);
            return 1;
        } else {
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                count += fullPermutation(head + str.substring(i, i + 1), str.substring(0, i) + str.substring(i + 1));//从str中选出一个字符加到head中
            }
            return count;
        }
    }

    private static int fullPermutation2(String str) {
        if (str != null && !str.isEmpty()) {
            return fullPermutation2(0, str, new char[str.length()], new boolean[str.length()]);
        } else {
            return 0;
        }
    }

    /**
     * @param step 第几步
     * @param str  需要全排序的字符串
     * @param arr  排序好的字符数组 每一步都用str中的字符填充arr[step]，直到最后arr被填充完
     * @param book 标记，用来标记str.charAt(i)是否被使用过，也就是在之前的步骤中已经被填充到arr中去了
     */
    private static int fullPermutation2(int step, String str, char[] arr, boolean[] book) {
        if (step == arr.length) {//结束，输出
            System.out.println(new String(arr));
            return 1;
        }

        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!book[i]) {//str中第i个字符没有用过
                book[i] = true;//str中第i个字符标记为已使用
                arr[step] = str.charAt(i);
                count += fullPermutation2(step + 1, str, arr, book);//进行下一步
                book[i] = false;//标记为未使用
            }
        }
        return count;
    }
}