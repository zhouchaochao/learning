package com.cc.leetCode;

import com.cc.leetCode.util.StringToArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: DungeonGame
 * Description: DungeonGame
 * https://leetcode-cn.com/problems/dungeon-game/
 * 174. 地下城游戏
 * Date:  2019/4/2
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class DungeonGame {

    private static final Logger logger = LoggerFactory.getLogger(DungeonGame.class);

    public static void main(String[] args) {
        String input = "[[-2,-3,3],[-5,-10,1],[10,30,-5]]";
        int[][] dungeon = StringToArrayUtil.twoDimensionArr(input);
        System.out.println(new DungeonGame().calculateMinimumHP(dungeon));

        input = "[[100]]";
        dungeon = StringToArrayUtil.twoDimensionArr(input);
        System.out.println(new DungeonGame().calculateMinimumHP(dungeon));

        input = "[[-1,1]]";
        dungeon = StringToArrayUtil.twoDimensionArr(input);
        System.out.println(new DungeonGame().calculateMinimumHP(dungeon));
    }

    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0) {
            return 1;
        }
        return calculateMinimumHP(dungeon, 0, 0, new java.util.HashMap<String, Integer>());
    }

    /**
     * 从（x,y）位置到dungeon右下角需要的hp
     *
     * @param dungeon
     * @param x
     * @param y
     * @param cache   已经计算出来的缓存
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon, int x, int y, java.util.Map<String, Integer> cache) {
        String key = x + "_" + y;
        Integer cachedValue = cache.get(key);
        if (cachedValue != null) {
            return cachedValue;
        }
        Integer value = null;
        //到了最后一个位置
        if (x == (dungeon.length - 1) && y == (dungeon[0].length - 1)) {
            value = -dungeon[x][y] + 1;
        } else {
            if (x < (dungeon.length - 1)) {//下边有点
                int tmp = calculateMinimumHP(dungeon, x + 1, y, cache) - dungeon[x][y];
                if (value == null || value >= tmp) {
                    value = tmp;
                }
            }
            if (value == null || value > 0) {
                if (y < (dungeon[0].length - 1)) {//右边有点
                    int tmp = calculateMinimumHP(dungeon, x, y + 1, cache) - dungeon[x][y];
                    if (value == null || value >= tmp) {
                        value = tmp;
                    }
                }
            }
        }

        if (value <= 0) {
            value = 1;
        }
        cache.put(key, value);
        return value;
    }

}
