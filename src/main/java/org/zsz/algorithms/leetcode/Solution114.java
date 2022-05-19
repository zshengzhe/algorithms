package org.zsz.algorithms.leetcode;

import java.util.Objects;

/**
 * 114. 二叉树展开为链表
 * <p>
 * https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
 *
 * @author Linus Zhang
 * @create 2022-05-18 22:49
 */
public class Solution114 {

  public void flatten(TreeNode root) {
    if (Objects.isNull(root)) {
      return;
    }
    doFlatten(root);
  }

  private TreeNode doFlatten(TreeNode node) {
    TreeNode left = node.left;
    TreeNode right = node.right;
    if (Objects.isNull(left) && Objects.isNull(right)) {
      return node;
    }

    node.right = left;
    node.left = null;
    TreeNode currentEnd = Objects.nonNull(node.right) ? doFlatten(node.right) : node;
    if (Objects.isNull(right)) {
      return currentEnd;
    }
    TreeNode end = doFlatten(right);
    currentEnd.right = right;
    return end;
  }

  private class TreeNode {

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
  }
}
