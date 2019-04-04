package com.cc.leetCode.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: TreeMaxRoad
 * Description: TreeMaxRoad
 *  124. 二叉树中的最大路径和
 *  https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/
 * Date:  2019/3/27
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class TreeMaxRoad {

    private static final Logger logger = LoggerFactory.getLogger(TreeMaxRoad.class);

    public TreeMaxRoad(){

    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(-3);
        System.out.println(new TreeMaxRoad().maxPathSum(treeNode));
    }

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode currentMax = new TreeNode(Integer.MIN_VALUE);
        maxPathUnderNode(root, currentMax);
        return currentMax.val;
    }

    /**
     * 以parent为路径一个端点的路径的最大值
     * 要么是左子树，要么是右子树
     *
     * @param parent
     * @return
     */
    public int maxPathUnderNode(TreeNode parent, TreeNode currentMax) {
        if (parent != null) {
            int leftMax = maxPathUnderNode(parent.left, currentMax);
            if (parent.left != null) {
                currentMax.val = leftMax > currentMax.val ? leftMax : currentMax.val;
            }

            int rightMax = maxPathUnderNode(parent.right, currentMax);
            if (parent.right != null) {
                currentMax.val = rightMax > currentMax.val ? rightMax : currentMax.val;
            }

            //左边加右边加当前节点形成的一条路径
            currentMax.val = (rightMax + leftMax + parent.val) > currentMax.val ? (rightMax + leftMax + parent.val) : currentMax.val;

            int result = parent.val;
            if (leftMax > 0 || rightMax > 0) {
                result += (leftMax > rightMax ? leftMax : rightMax);
            }
            currentMax.val = result > currentMax.val ? result : currentMax.val;

            return result;

        } else {
            return 0;
        }
    }
}
