package com.cc.leetCode;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: 230. 二叉搜索树中第K小的元素
 * https://leetcode.cn/problems/kth-smallest-element-in-a-bst/?envType=study-plan-v2&envId=top-interview-150
 * @date: 2024-04-09
 */

public class KthSmallestElementInABst {

    private int index=0;
    private TreeNode ans;
    public int kthSmallest(TreeNode root, int k) {
        midRead(root,k);
        return ans.val;
    }

    private void midRead(final TreeNode root, int k) {
        if(root==null){
            return;
        }
        midRead(root.left,k);
        if(++index==k){
            ans=root;
            return;
        }
        midRead(root.right,k);
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
