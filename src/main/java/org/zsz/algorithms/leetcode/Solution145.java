package org.zsz.algorithms.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 145. 二叉树的后序遍历
 * <p>
 * https://leetcode.cn/problems/binary-tree-postorder-traversal/
 *
 * @author Linus Zhang
 * @create 2022-05-17 22:28
 */
public class Solution145 {

  public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> list = new LinkedList<>();
    doPostorderTraversal(root, list);
    return list;
  }

  private void doPostorderTraversal(TreeNode root, List<Integer> list) {
    if (Objects.isNull(root)) {
      return;
    }

    doPostorderTraversal(root.left, list);
    doPostorderTraversal(root.right, list);
    list.add(root.val);
  }

  private static class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    @Override
    public String toString() {
      return "TreeNode{" +
          "val=" + val +
          '}';
    }
  }

}
