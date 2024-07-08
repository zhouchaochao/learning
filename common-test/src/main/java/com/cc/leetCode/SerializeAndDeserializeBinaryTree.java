package com.cc.leetCode;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: SerializeAndDeserializeBinaryTree
 * https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
 * 297. 二叉树的序列化与反序列化
 * @date: 2023-07-04
 */

public class SerializeAndDeserializeBinaryTree {

    public static void main(String[] args) {
        SerializeAndDeserializeBinaryTree ser = new SerializeAndDeserializeBinaryTree();
        SerializeAndDeserializeBinaryTree deser = new SerializeAndDeserializeBinaryTree();

        TreeNode root = new TreeNode(100);
        System.out.println(ser.serialize(root));

        root.left = new TreeNode(200);
        System.out.println(ser.serialize(root));

        root.left = null;
        root.right = new TreeNode(300);
        System.out.println(ser.serialize(root));

        root.left = new TreeNode(200);
        root.right = new TreeNode(300);
        System.out.println(ser.serialize(root));

        root.left = new TreeNode(200);
        root.right = new TreeNode(300);
        root.left.left = new TreeNode(400);
        System.out.println(ser.serialize(root));

        root.left = new TreeNode(200);
        root.right = new TreeNode(300);
        root.left.left = new TreeNode(400);
        root.left.right = new TreeNode(500);
        root.right.left = new TreeNode(600);
        root.right.right = new TreeNode(700);
        System.out.println(ser.serialize(root));

        String s = "100,4,200,1,400,500,300,1,600,700";
        root = deser.deserialize(s);
        System.out.println(ser.serialize(root));

        //TreeNode ans = deser.deserialize(ser.serialize(root));
        System.out.println(String.join(",", Collections.EMPTY_LIST));
        TreeNode t = deser.deserialize(String.join(",", Collections.EMPTY_LIST));
    }

    /**
     按照数组表示
     一个节点的表示协议: [rootValue, 【leftChildSize】,【leftChild】,【rightChild】]
     如果没有孩子节点，leftChildSize, leftChild, rightChild 不存在，表示为 [rootValue]
     如果没有左孩子节点，leftChildSize=0，leftChild=EMPTY_LIST
     如果没有右孩子节点，leftChildSize=leftChild.length, rightChild=EMPTY_LIST

     举例：
        一个节点100的树的表示 [100]
        节点100,包含左孩子200 的表示 [100,1,200]
        节点100,包含右孩子300 的表示 [100,0,300]
        节点100,包含左孩子200，右孩子300 的表示 [100,1,200,300]
        节点100,包含左孩子200，右孩子300，200包含左孩子400，300包含右孩子700 的表示 [100,3,200,1,400,300,0,700]
     */

    public String serialize(TreeNode root) {
        return String.join(",", treeToArr(root));
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] arr = data.split(",");
        return buildNode(arr, 0, arr.length);
    }

    // [from,to)
    private TreeNode buildNode(String[] arr, int from, int to) {
        if (to - from < 1) {
            return null;
        } else if (to - from == 1) {
            return new TreeNode(Integer.valueOf(arr[from]));
        } else {
            TreeNode parent = new TreeNode(Integer.valueOf(arr[from]));
            int leftChildSize = Integer.valueOf(arr[from+1]);
            parent.left = buildNode(arr, from + 2, from + 2 + leftChildSize);
            parent.right = buildNode(arr, from + 2 + leftChildSize, to);
            return parent;
        }
    }

    private List<String> treeToArr(TreeNode treeNode) {
        if (treeNode == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> arr = new ArrayList<>();
        arr.add(String.valueOf(treeNode.val));

        if (!(treeNode.left == null && treeNode.right == null)) {
            List<String> left = treeToArr(treeNode.left);
            List<String> right = treeToArr(treeNode.right);
            arr.add(String.valueOf(left.size()));
            arr.addAll(left);
            arr.addAll(right);
        }
        return arr;
    }

    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
}
