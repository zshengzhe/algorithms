package org.zsz.algorithms.leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 590. N 叉树的后序遍历
 * <p>
 * https://leetcode.cn/problems/n-ary-tree-postorder-traversal/
 *
 * @author Linus Zhang
 * @create 2022-05-18 22:15
 */
public class Solution590 {

  public List<Integer> postorder(Node root) {
    List<Integer> list = new LinkedList<>();
    if (Objects.isNull(root)) {
      return list;
    }
    Deque<Helper> stack = new LinkedList<>();
    stack.push(new Helper(root, false));
    while (!stack.isEmpty()) {
      Helper peek = stack.peek();
      Node node = peek.node;
      if (peek.childrenAdded) {
        stack.pop();
        list.add(node.val);
        continue;
      }
      List<Node> children = node.children;
      if (Objects.nonNull(children)) {
        for (int i = children.size() - 1; i >= 0; i--) {
          stack.push(new Helper(children.get(i), false));
        }
      }
      peek.childrenAdded = true;
    }
    return list;
  }

  private void doPostorder(Node root, List<Integer> list) {
    if (Objects.isNull(root)) {
      return;
    }
    List<Node> children = root.children;
    if (Objects.nonNull(children)) {
      for (Node node : children) {
        doPostorder(node, list);
      }
    }
    list.add(root.val);
  }

  private static class Helper {

    Node node;
    boolean childrenAdded;

    public Helper(Node node, boolean childrenAdded) {
      this.node = node;
      this.childrenAdded = childrenAdded;
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

    public Node(int val, List<Node> children) {
      this.val = val;
      this.children = children;
    }
  }
}
