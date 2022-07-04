package org.zsz.algorithms.tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.zsz.algorithms.support.TypeSupport;
import org.zsz.algorithms.support.printer.BinaryTreeInfo;

/**
 * 二叉树
 *
 * @author Linus Zhang
 * @create 2022-05-16 22:54
 */
public class BinaryTree<E> implements BinaryTreeInfo {

  protected int size;

  protected Node<E> root;

  public BinaryTree() {
    this.size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  protected Node<E> createNode(E element) {
    return Node.create(element);
  }

  protected Node<E> createNode(E element, Node<E> parent) {
    return Node.create(element, parent);
  }

  /**
   * 前驱节点 <br/> 中序遍历的前驱节点
   *
   * @param node 节点
   * @return 前驱节点
   */
  protected Node<E> predecessor(Node<E> node) {
    if (Objects.isNull(node)) {
      return null;
    }

    // L-R-R-R-R
    if (Objects.nonNull(node.left)) {
      Node<E> predecessor = node.left;
      while (Objects.nonNull(predecessor.right)) {
        predecessor = predecessor.right;
      }
      return predecessor;
    }

    // node是父节点的右孩子
    while (Objects.nonNull(node.parent) && Objects.equals(node, node.parent.left)) {
      node = node.parent;
    }
    return node.parent;
  }

  /**
   * 后继节点 <br/> 中序遍历的后继节点
   *
   * @param node 节点
   * @return 后继节点
   */
  protected Node<E> successor(Node<E> node) {
    if (Objects.isNull(node)) {
      return null;
    }

    // R-L-L-L-L
    if (Objects.nonNull(node.right)) {
      Node<E> successor = node.right;
      while (Objects.nonNull(successor.left)) {
        successor = successor.left;
      }
      return successor;
    }

    // node是父节点的左孩子
    while (Objects.nonNull(node.parent) && Objects.equals(node, node.parent.right)) {
      node = node.parent;
    }
    return node.parent;
  }

  public void preorder(Consumer<E> processor) {
    if (Objects.isNull(processor) || isEmpty()) {
      return;
    }
    doPreorder(root, processor);
  }

  private void doPreorder(Node<E> node, Consumer<E> processor) {
    if (Objects.isNull(node)) {
      return;
    }
    processor.accept(node.element);
    doPreorder(node.left, processor);
    doPreorder(node.right, processor);
  }

  public void inorder(Consumer<E> processor) {
    if (Objects.isNull(processor) || isEmpty()) {
      return;
    }
    doInorder(root, processor);
  }

  private void doInorder(Node<E> node, Consumer<E> processor) {
    if (Objects.isNull(node)) {
      return;
    }
    doInorder(node.left, processor);
    processor.accept(node.element);
    doInorder(node.right, processor);
  }

  public void postorder(Consumer<E> processor) {
    if (Objects.isNull(processor) || isEmpty()) {
      return;
    }
    doPostorder(root, processor);
  }

  private void doPostorder(Node<E> node, Consumer<E> processor) {
    if (Objects.isNull(node)) {
      return;
    }
    doPostorder(node.left, processor);
    doPostorder(node.right, processor);
    processor.accept(node.element);
  }

  public void leverOrder(Consumer<E> processor) {
    if (Objects.isNull(processor) || isEmpty()) {
      return;
    }

    Queue<Node<E>> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      Node<E> node = queue.poll();

      processor.accept(node.element);

      Optional.ofNullable(node.left)
          .ifPresent(queue::offer);

      Optional.ofNullable(node.right)
          .ifPresent(queue::offer);
    }
  }

  public int height() {
    int height = 0;
    if (isEmpty()) {
      return height;
    }

    Queue<Node<E>> queue = new LinkedList<>();
    queue.offer(root);
    int levelSize;
    while (!queue.isEmpty()) {
      levelSize = queue.size();

      for (int i = 0; i < levelSize; i++) {
        Node<E> node = Optional.ofNullable(queue.poll())
            .orElseThrow(NullPointerException::new);

        Optional.ofNullable(node.left)
            .ifPresent(queue::offer);

        Optional.ofNullable(node.right)
            .ifPresent(queue::offer);
      }

      height++;
    }
    return height;
  }

  public int heightRecursion(Node<E> node) {
    if (Objects.isNull(node)) {
      return 0;
    }
    return 1 + Math.max(heightRecursion(node.left), heightRecursion(node.right));
  }

  /**
   * 是否完全二叉树
   * <p>
   * 思路: 层序遍历
   * <p>
   * 1. 度为2加入queue
   * <p>
   * 2. 左孩子为空 && 右孩子不为空 不是完全二叉树
   * <p>
   * 3. 左孩子不为空 && 右孩子为空 后续节点都要是叶子节点才符合完全二叉树性质
   *
   * @return true or false
   */
  public boolean isComplete() {
    if (Objects.isNull(root)) {
      return false;
    }

    Queue<Node<E>> queue = new LinkedList<>();
    queue.offer(root);

    boolean mustLeaf = false;
    while (!queue.isEmpty()) {
      Node<E> node = queue.poll();

      if (mustLeaf && node.isNotLeaf()) {
        return false;
      }

      Node<E> left = node.left;
      Node<E> right = node.right;

      if (Objects.nonNull(left)) {
        queue.offer(left);
      }
      // left为空 right不为空 不是完全二叉树
      else if (Objects.nonNull(right)) {
        return false;
      }

      if (Objects.nonNull(right)) {
        queue.offer(right);
      } else {
        // right == null
        mustLeaf = true;
      }

    }

    return true;
  }

  @Override
  public Object root() {
    return root;
  }

  @Override
  public Object left(Object node) {
    return Node.cast(node).left;
  }

  @Override
  public Object right(Object node) {
    return Node.cast(node).right;
  }

  @Override
  public Object string(Object node) {
    return Node.cast(node).toString();
  }

  @Data
  @Accessors(chain = true)
  protected static class Node<E> {

    E element;

    Node<E> left;

    Node<E> right;

    Node<E> parent;

    static <E> Node<E> create(E element, Node<E> parent) {
      return new Node<E>()
          .setElement(element)
          .setParent(parent);
    }

    static <E> Node<E> cast(Object node) {
      return TypeSupport.unsafeCast(node);
    }

    static <E> Node<E> create(E element) {
      return create(element, TypeSupport.returnNull());
    }

    boolean isLeaf() {
      return Objects.isNull(left) && Objects.isNull(right);
    }

    boolean isNotLeaf() {
      return !isLeaf();
    }

    boolean degreeTwo() {
      return Objects.nonNull(left) && Objects.nonNull(right);
    }

    boolean isLeftChild() {
      return Objects.nonNull(parent) && this == parent.left;
    }

    boolean isRightChild() {
      return Objects.nonNull(parent) && this == parent.right;
    }

    boolean isRoot() {
      return Objects.isNull(parent);
    }

    Node<E> sibling() {
      if (isLeftChild()) {
        return parent.right;
      } else if (isRightChild()) {
        return parent.left;
      }
      return TypeSupport.returnNull();
    }

    @Override
    public String toString() {
      return element.toString();
    }

  }

}
