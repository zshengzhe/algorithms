package org.zsz.algorithms.leetcode;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 662. 二叉树最大宽度
 * <p>
 * https://leetcode.cn/problems/maximum-width-of-binary-tree/
 *
 * @author Linus Zhang
 * @create 2022-05-15 21:32
 */
public class Solution662 {

  public int widthOfBinaryTree(TreeNode root) {
    if (Objects.isNull(root)) {
      return 0;
    }

    Queue<WidthNode> queue = new LinkedList<>();
    queue.offer(new WidthNode(root, 0));
    int result = 0;
    int levelSize;
    while (!queue.isEmpty()) {
      levelSize = queue.size();

      int leftMax = 0;
      int rightMax = 0;
      for (int i = 0; i < levelSize; i++) {
        WidthNode node = queue.poll();
        TreeNode treeNode = node.node;
        int score = node.score;

        if (i == 0) {
          leftMax = score;
        }
        if (i == levelSize - 1) {
          rightMax = score;
        }

        TreeNode left = treeNode.left;
        TreeNode right = treeNode.right;

        if (Objects.nonNull(left)) {
          queue.offer(new WidthNode(left, leftScore(score)));
        }
        if (Objects.nonNull(right)) {
          queue.offer(new WidthNode(right, rightScore(score)));
        }
      }

      result = Math.max(result, eval(leftMax, rightMax));
    }

    return result;
  }

  private int eval(int leftMax, int rightMax) {
    if (leftMax == rightMax) {
      return 1;
    }
    if ((leftMax < 0 && rightMax < 0) || (leftMax > 0 && rightMax > 0)) {
      return Math.max(leftMax, rightMax) - Math.min(leftMax, rightMax) + 1;
    } else {
      return Math.abs(leftMax) + Math.abs(rightMax);
    }
  }

  private int leftScore(int score) {
    if (score == 0) {
      return -1;
    }
//    if (score < 0) {
//      return 2 * score;
//    } else {
//      return 2 * score - 1;
//    }
    score <<= 1;
    return score < 0 ? score : score - 1;
  }

  private int rightScore(int score) {
    if (score == 0) {
      return 1;
    }
//    if (score < 0) {
//      return 2 * score + 1;
//    } else {
//      return 2 * score;
//    }
    score <<= 1;
    return score < 0 ? score + 1 : score;
  }

  private static class WidthNode {

    TreeNode node;

    int score;

    WidthNode(TreeNode node, int score) {
      this.node = node;
      this.score = score;
    }

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
