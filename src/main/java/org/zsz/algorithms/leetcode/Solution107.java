package org.zsz.algorithms.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * 107. 二叉树的层序遍历 II
 * <p>
 * https://leetcode.cn/problems/binary-tree-level-order-traversal-ii/
 *
 * @author Linus Zhang
 * @create 2022-05-15 21:25
 */
public class Solution107 {

  public List<List<Integer>> levelOrderBottom(TreeNode root) {
    LinkedList<List<Integer>> result = new LinkedList<>();

    if (Objects.isNull(root)) {
      return result;
    }
    Queue<TreeNode> queue = new java.util.LinkedList<>();
    queue.offer(root);
    int levelSize;
    while (!queue.isEmpty()) {
      levelSize = queue.size();
      List<Integer> level = new LinkedList<>();
      for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        level.add(node.val);
        if (Objects.nonNull(node.left)) {
          queue.offer(node.left);
        }

        if (Objects.nonNull(node.right)) {
          queue.offer(node.right);
        }
      }
      result.addFirst(level);
    }
    return result;
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
