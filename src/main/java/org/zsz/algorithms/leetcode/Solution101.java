package org.zsz.algorithms.leetcode;

import java.util.Objects;

/**
 * 101. 对称二叉树
 * <p>
 * https://leetcode.cn/problems/symmetric-tree/
 *
 * @author Linus Zhang
 * @create 2022-05-19 00:21
 */
public class Solution101 {

  public boolean isSymmetric(TreeNode root) {
    if (Objects.isNull(root)) {
      return false;
    }
    return isSymmetric(root.left, root.right);
  }

  private boolean isSymmetric(TreeNode node1, TreeNode node2) {
    if (Objects.isNull(node1)) {
      return Objects.isNull(node2);
    }
    if (Objects.isNull(node2)) {
      return false;
    }
    if (node1.val != node2.val) {
      return false;
    }
    return isSymmetric(node1.left, node2.right) && isSymmetric(node1.right, node2.left);
  }

  public class TreeNode {

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
