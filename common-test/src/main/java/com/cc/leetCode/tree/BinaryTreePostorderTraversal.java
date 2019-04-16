package com.cc.leetCode.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: BinaryTreePostorderTraversal
 * Description: BinaryTreePostorderTraversal
 * 145. 二叉树的后序遍历
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
 * Date:  2019/4/10
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class BinaryTreePostorderTraversal {

    //进阶: 递归算法很简单，你可以通过迭代算法完成吗？
    public static void main(String[] args) {
        TreeNode parent = new TreeNode(1);
        parent.right = new TreeNode(2);
        parent.right.left = new TreeNode(3);
        System.out.println(new BinaryTreePostorderTraversal().postorderTraversal(parent));
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        //postorderTraversal(root,list);
        //postorderTraversalIteration(root,list);
        postorderTraversalIterationNoHarm(root,list);
        return list;
    }

    //递归算法
    public void postorderTraversal(TreeNode parent,List<Integer> list) {
        if (parent == null) {
            return;
        }
        postorderTraversal(parent.left,list);
        postorderTraversal(parent.right,list);
        list.add(parent.val);
    }

    //迭代算法-破坏了树
    public void postorderTraversalIteration(TreeNode parent,List<Integer> list) {
        if (parent == null) {
            return;
        }
        List<TreeNode> tmp = new ArrayList<>();
        tmp.add(parent);
        while (!tmp.isEmpty()) {
            TreeNode last = tmp.get(tmp.size() - 1);
            if (last.right == null && last.left == null) {
                list.add(last.val);
                tmp.remove(tmp.size() - 1);
                continue;
            }
            if (last.right != null) {
                tmp.add(last.right);
                last.right = null;//破坏了树
            }
            if (last.left != null) {
                tmp.add(last.left);
                last.left = null;//破坏了树
            }
        }
    }


    //迭代算法-不破坏树
    public void postorderTraversalIterationNoHarm(TreeNode parent,List<Integer> list) {
        if (parent == null) {
            return;
        }
        java.util.Stack<TreeNode> hasAddChild = new java.util.Stack<>();
        List<TreeNode> tmp = new ArrayList<>();
        tmp.add(parent);
        while (!tmp.isEmpty()) {
            TreeNode last = tmp.get(tmp.size() - 1);
            if (!hasAddChild.isEmpty()) {
                TreeNode current = hasAddChild.peek();
                if (last.equals(current)) {
                    hasAddChild.pop();
                    list.add(last.val);
                    tmp.remove(tmp.size() - 1);
                    continue;
                }
            }

            if (last.right == null && last.left == null) {
                list.add(last.val);
                tmp.remove(tmp.size() - 1);
                continue;
            } else {
                if (last.right != null) {
                    tmp.add(last.right);
                }
                if (last.left != null) {
                    tmp.add(last.left);
                }
                hasAddChild.push(last);
            }
        }
    }

}
