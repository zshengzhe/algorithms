package org.zsz.algorithms.leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 144. 二叉树的前序遍历
 * <p>
 * https://leetcode.cn/problems/binary-tree-preorder-traversal/
 *
 * @author Linus Zhang
 * @create 2022-05-16 23:01
 */
public class Solution144 {

  public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new LinkedList<>();
    if (Objects.isNull(root)) {
      return result;
    }

    Deque<TreeNode> stack = new LinkedList<>();
    stack.add(root);
    while (!stack.isEmpty()) {
      TreeNode top = stack.pollFirst();
      result.add(top.val);

      Optional.ofNullable(top.right)
          .ifPresent(stack::addFirst);
      Optional.ofNullable(top.left)
          .ifPresent(stack::addFirst);
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
