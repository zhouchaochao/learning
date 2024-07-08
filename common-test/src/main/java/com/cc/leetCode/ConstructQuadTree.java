package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 427. 建立四叉树
 * https://leetcode.cn/problems/construct-quad-tree/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-12
 */

public class ConstructQuadTree {

    public Node construct(int[][] grid) {
        return dfs(grid, 0, 0, grid.length, grid.length);
    }

    public Node dfs(int[][] grid, int r0, int c0, int r1, int c1) {
        boolean same = true;
        for (int i = r0; i < r1; ++i) {
            for (int j = c0; j < c1; ++j) {
                if (grid[i][j] != grid[r0][c0]) {
                    same = false;
                    break;
                }
            }
            if (!same) {
                break;
            }
        }

        if (same) {
            return new Node(grid[r0][c0] == 1, true);
        }

        Node ret = new Node(
                true,
                false,
                dfs(grid, r0, c0, (r0 + r1) / 2, (c0 + c1) / 2),
                dfs(grid, r0, (c0 + c1) / 2, (r0 + r1) / 2, c1),
                dfs(grid, (r0 + r1) / 2, c0, r1, (c0 + c1) / 2),
                dfs(grid, (r0 + r1) / 2, (c0 + c1) / 2, r1, c1)
        );
        return ret;
    }

// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;


    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}
}
