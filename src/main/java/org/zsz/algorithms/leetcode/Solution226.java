package org.zsz.algorithms.leetcode;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

/**
 * 226. 翻转二叉树
 * <p>
 * https://leetcode.cn/problems/invert-binary-tree/
 *
 * @author Linus Zhang
 * @create 2022-05-11 23:10
 */
public class Solution226 {

  public TreeNode invertTreeRecursion(TreeNode root) {
    if (Objects.isNull(root)) {
      return root;
    }

    TreeNode tmp = root.left;
    root.left = root.right;
    root.right = tmp;
    invertTreeRecursion(root.left);
    invertTreeRecursion(root.right);

    return root;
  }

  public TreeNode invertTreeIterate(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();

      TreeNode tmp = node.left;
      node.left = node.right;
      node.right = tmp;

      Optional.ofNullable(node.left)
          .ifPresent(queue::offer);

      Optional.ofNullable(node.right)
          .ifPresent(queue::offer);
    }
    return root;
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
