package org.zsz.algorithms.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * 559. N 叉树的最大深度
 * <p>
 * https://leetcode.cn/problems/maximum-depth-of-n-ary-tree/
 *
 * @author Linus Zhang
 * @create 2022-05-18 22:31
 */
public class Solution559 {

  public int maxDepth(Node root) {
    if (Objects.isNull(root)) {
      return 0;
    }

    Queue<Node> queue = new LinkedList<>();
    queue.offer(root);
    int levelSize;
    int depth = 0;
    while (!queue.isEmpty()) {
      levelSize = queue.size();
      for (int i = 0; i < levelSize; i++) {
        Node node = queue.poll();

        List<Node> children = node.children;
        if (Objects.nonNull(children)) {
          queue.addAll(children);
        }
      }
      depth++;
    }
    return depth;
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
