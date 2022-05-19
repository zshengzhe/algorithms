package org.zsz.algorithms.leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 589. N 叉树的前序遍历
 * <p>
 * https://leetcode.cn/problems/n-ary-tree-preorder-traversal/
 *
 * @author Linus Zhang
 * @create 2022-05-18 21:47
 */
public class Solution589 {

  public List<Integer> preorder(Node root) {
    List<Integer> list = new LinkedList<>();
    if (Objects.isNull(root)) {
      return list;
    }
    Deque<Node> stack = new LinkedList<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      list.add(node.val);
      List<Node> children = node.children;
      if (Objects.nonNull(children)) {
        for (int i = children.size() - 1; i >= 0; i--) {
          stack.push(children.get(i));
        }
      }
    }
    return list;
  }

  private void doPreorder(Node root, List<Integer> list) {
    if (Objects.isNull(root)) {
      return;
    }
    list.add(root.val);
    List<Node> children = root.children;
    if (Objects.nonNull(children)) {
      for (Node node : children) {
        doPreorder(node, list);
      }
    }
  }

  private class Node {

    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int val) {
      this.val = val;
    }

    public Node(int val, List<Node> _children) {
      this.val = val;
      children = _children;
    }
  }

}
