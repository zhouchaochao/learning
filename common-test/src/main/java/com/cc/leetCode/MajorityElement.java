package com.cc.leetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 169. 多数元素
 * https://leetcode.cn/problems/majority-element/description/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-03-27
 */

public class MajorityElement {

    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }
}
