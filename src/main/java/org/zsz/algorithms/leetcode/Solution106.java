package org.zsz.algorithms.leetcode;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 * <p>
 * https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 *
 * @author Linus Zhang
 * @create 2022-05-18 23:43
 */
public class Solution106 {

  public TreeNode buildTree(int[] inorder, int[] postorder) {
    if (inorder == null || inorder.length == 0) {
      return null;
    }
    int arrLength = inorder.length;
    if (arrLength == 1) {
      return new TreeNode(inorder[0]);
    }
    int parent = postorder[arrLength - 1];

    int leftLength;

    for (leftLength = 0; leftLength < arrLength; leftLength++) {
      if (parent == inorder[leftLength]) {
        break;
      }
    }
    int rightLength = arrLength - 1 - leftLength;

    int[] leftInorder = new int[leftLength];
    int[] rightInorder = new int[rightLength];

    int[] leftPostorder = new int[leftLength];
    int[] rightPostorder = new int[rightLength];

    System.arraycopy(inorder, 0, leftInorder, 0, leftLength);
    System.arraycopy(inorder, leftLength + 1, rightInorder, 0, rightLength);

    System.arraycopy(postorder, 0, leftPostorder, 0, leftLength);
    System.arraycopy(postorder, leftLength, rightPostorder, 0, rightLength);

    TreeNode root = new TreeNode(parent);
    root.left = buildTree(leftInorder, leftPostorder);
    root.right = buildTree(rightInorder, rightPostorder);
    return root;
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
