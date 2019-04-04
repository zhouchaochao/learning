package com.cc.leetCode;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: PoorPigs
 * Description: PoorPigs
 * 458. 可怜的小猪
 * https://leetcode-cn.com/problems/poor-pigs/
 * Date:  2019/4/2
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class PoorPigs {

    private static final Logger logger = LoggerFactory.getLogger(PoorPigs.class);

    public static void main(String[] args) {
        /*
        假设有 n 只水桶，猪饮水中毒后会在 m 分钟内死亡，
        你需要多少猪（x）就能在 p 分钟内找出 “有毒” 水桶？
        这 n 只水桶里有且仅有一只有毒的桶。
        */

        System.out.println("" + new PoorPigs().poorPigs(16,2,4));
        System.out.println("" + new PoorPigs().poorPigs(1000, 15, 60));
    }

    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        if (minutesToTest < minutesToDie) {
            return 0;
        }
        int times = minutesToTest/minutesToDie;
        int pigs = 1;
        while (Math.pow(Math.pow(2, pigs), times) < buckets) {
            pigs++;
        }
        return pigs;
    }

}
