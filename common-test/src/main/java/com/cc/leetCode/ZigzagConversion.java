package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 6. Z 字形变换
 * https://leetcode.cn/problems/zigzag-conversion/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-01
 */

public class ZigzagConversion {

    public String convert(String s, int numRows) {
        if(numRows < 2) {return s;}
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for(int i = 0; i < numRows; i++) { rows.add(new StringBuilder()); };
        int i = 0, flag = -1;
        for(char c : s.toCharArray()) {
            rows.get(i).append(c);
            if(i == 0 || i == numRows -1) {flag = - flag;}
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for(StringBuilder row : rows) {res.append(row);}
        return res.toString();
    }

}
