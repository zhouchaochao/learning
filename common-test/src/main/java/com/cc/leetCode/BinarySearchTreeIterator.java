package com.cc.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 173. 二叉搜索树迭代器
 * https://leetcode.cn/problems/binary-search-tree-iterator/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-09
 */

public class BinarySearchTreeIterator {

    private int idx;
    private List<Integer> arr;

    public BinarySearchTreeIterator(TreeNode root) {
        idx = 0;
        arr = new ArrayList<Integer>();
        inorderTraversal(root, arr);
    }

    public int next() {
        return arr.get(idx++);
    }

    public boolean hasNext() {
        return idx < arr.size();
    }

    private void inorderTraversal(TreeNode root, List<Integer> arr) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left, arr);
        arr.add(root.val);
        inorderTraversal(root.right, arr);
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
