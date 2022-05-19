package org.zsz.algorithms.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * 94. 二叉树的中序遍历
 * <p>
 * https://leetcode.cn/problems/binary-tree-inorder-traversal/
 *
 * @author Linus Zhang
 * @create 2022-05-16 23:12
 */
public class Solution94 {

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new LinkedList<>();
    if (Objects.isNull(root)) {
      return result;
    }

    Stack<TreeNode> stack = new Stack<>();
    TreeNode node = root;
    while (!stack.isEmpty() || Objects.nonNull(node)) {
      // L-L-L-L...
      while (Objects.nonNull(node)) {
        stack.push(node);
        node = node.left;
      }
      // M
      node = stack.pop();
      result.add(node.val);
      // R
      node = node.right;
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

    @Override
    public String toString() {
      return "TreeNode{" +
          "val=" + val +
          '}';
    }
  }

}
