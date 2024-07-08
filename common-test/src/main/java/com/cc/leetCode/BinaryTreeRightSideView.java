package com.cc.leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 199. 二叉树的右视图
 * https://leetcode.cn/problems/binary-tree-right-side-view/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-09
 */

public class BinaryTreeRightSideView {

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {return ans;}
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int count;
        while (!q.isEmpty()) {
            count = q.size();
            for (int i = 0; i < count; i++) {
                TreeNode node = q.poll();
                if (node.left != null) {q.offer(node.left);}
                if (node.right != null) {q.offer(node.right);}
                if (i == count - 1) {ans.add(node.val);}
            }
        }
        return ans;
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }

}
