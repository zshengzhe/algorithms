package org.zsz.algorithms.leetcode;

import java.util.Objects;

/**
 * 104. 二叉树的最大深度
 * <p>
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/
 *
 * @author Linus Zhang
 * @create 2022-05-15 21:18
 */
public class Solution104 {

  public int maxDepth(TreeNode root) {
    if (Objects.isNull(root)) {
      return 0;
    }

    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
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
  }

}
