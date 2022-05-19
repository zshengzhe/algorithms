package org.zsz.algorithms.leetcode;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 * <p>
 * https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 *
 * @author Linus Zhang
 * @create 2022-05-19 22:23
 */
public class Solution105 {

  public TreeNode buildTree(int[] preorder, int[] inorder) {
    if (inorder == null || inorder.length == 0) {
      return null;
    }
    int arrLength = preorder.length;
    if (arrLength == 1) {
      return new TreeNode(preorder[0]);
    }
    int parent = preorder[0];

    int leftLength;

    for (leftLength = 0; leftLength < arrLength; leftLength++) {
      if (parent == inorder[leftLength]) {
        break;
      }
    }
    int rightLength = arrLength - 1 - leftLength;

    int[] leftPreorder = new int[leftLength];
    int[] rightPreorder = new int[rightLength];

    int[] leftInorder = new int[leftLength];
    int[] rightInorder = new int[rightLength];

    System.arraycopy(preorder, 1, leftPreorder, 0, leftLength);
    System.arraycopy(preorder, leftLength + 1, rightPreorder, 0, rightLength);

    System.arraycopy(inorder, 0, leftInorder, 0, leftLength);
    System.arraycopy(inorder, leftLength + 1, rightInorder, 0, rightLength);

    TreeNode root = new TreeNode(parent);
    root.left = buildTree(leftPreorder, leftInorder);
    root.right = buildTree(rightPreorder, rightInorder);
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
