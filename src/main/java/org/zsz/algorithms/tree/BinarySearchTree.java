package org.zsz.algorithms.tree;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Supplier;

/**
 * 二叉搜索树
 *
 * @author Linus Zhang
 * @create 2022-05-09 22:37
 */
@SuppressWarnings("unchecked")
public class BinarySearchTree<E> extends BinaryTree<E> {

  private final Comparator<E> comparator;

  public BinarySearchTree() {
    this(null);
  }

  public BinarySearchTree(Comparator<E> comparator) {
    super();
    this.comparator = comparator;
  }

  public int size() {
    return size;
  }

  public void add(E element) {
    if (Objects.isNull(root)) {
      root = createNode(assertNotNull(element));
      size++;
      postAddProcess(root);
      return;
    }

    Node<E> parent = root;
    Node<E> node = root;
    int cmp = 0;
    while (Objects.nonNull(node)) {
      parent = node;

      cmp = compare(element, node.element);
      if (cmp == 0) {
        node.element = element;
        return;
      }

      node = cmp > 0 ? node.right : node.left;
    }

    Node<E> newNode = createNode(element, parent);
    if (cmp > 0) {
      parent.right = newNode;
    } else {
      parent.left = newNode;
    }
    size++;
    postAddProcess(newNode);
  }

  protected void postAddProcess(Node<E> node) {
    // 子类可通过该方法调整结构
  }

  public void remove(E element) {
    node(element).ifPresent(this::remove);
  }

  private void remove(Node<E> node) {
    if (Objects.isNull(node)) {
      return;
    }

    // 度为2
    if (node.degreeTwo()) {
      Node<E> successor = successor(node);
      Preconditions.checkNotNull(successor);
      node.element = successor.element;
      node = successor;
    }
    Node<E> parent = node.parent;

    // 度为2或0
    Node<E> child = Optional.ofNullable(node.left)
        .orElse(node.right);
    // 度为1 找到子节点替换
    if (Objects.nonNull(child)) {
      child.parent = parent;
      if (Objects.isNull(parent)) {
        root = child;
      }
      // child在哪边让父节点指向谁
      else if (node.isLeftChild()) {
        parent.left = child;
      } else {
        parent.right = child;
      }

      postRemoveProcess(child);
    }
    // 根节点 度为0
    else if (Objects.isNull(parent)) {
      root = null;

      postRemoveProcess(node);
    }
    // 叶子节点
    else {
      // child在哪边让父节点指向谁
      if (node.isLeftChild()) {
        parent.left = null;
      } else {
        parent.right = null;
      }

      postRemoveProcess(node);
    }

    size--;
  }

  protected void postRemoveProcess(Node<E> node) {
    // 子类可通过该方法调整结构
  }

  public boolean contains(E element) {
    return node(element).isPresent();
  }

  private Optional<Node<E>> node(E element) {
    Node<E> node = root;
    while (Objects.nonNull(node)) {
      int cmp = compare(element, node.element);
      if (cmp == 0) {
        return Optional.of(node);
      }

      node = cmp > 0 ? node.right : node.left;
    }
    return Optional.empty();
  }

  private E assertNotNull(E element) {
    return Objects.requireNonNull(element, "element must not null");
  }

  private int compare(E e1, E e2) {
    Supplier<Integer> compare = () -> ((Comparable<E>) e1).compareTo(e2);
    return Optional.ofNullable(comparator)
        .map(cmp -> cmp.compare(e1, e2))
        .orElseGet(compare);
  }

  public void clear() {
    size = 0;
    if (Objects.isNull(root)) {
      return;
    }

    Queue<Node<E>> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      Node<E> node = queue.poll();

      Optional.ofNullable(node.left)
          .ifPresent(queue::offer);

      Optional.ofNullable(node.right)
          .ifPresent(queue::offer);

      // clear help gc
      node.parent = null;
      node.element = null;
      node.left = null;
      node.right = null;
    }
    root = null;
  }

}
