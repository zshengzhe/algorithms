package org.zsz.algorithms.tree;

import java.util.Comparator;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.zsz.algorithms.support.TypeSupport;

/**
 * AVL树
 *
 * @author Linus Zhang
 * @create 2022-05-19 22:55
 */
public class AvlTree<E> extends BalancedBinarySearchTree<E> {

  public AvlTree() {
    this(TypeSupport.returnNull());
  }

  public AvlTree(Comparator<E> comparator) {
    super(comparator);
  }

  @Override
  protected void postAddProcess(Node<E> node) {
    while ((node = node.parent) != null) {
      AvlNode<E> avlNode = AvlNode.cast(node);
      // 平衡
      if (avlNode.isBalanced()) {
        // 更新高度
        avlNode.updateHeight();
      }
      // 不平衡
      else {
        // 恢复平衡
        rebalance(node);
        break;
      }
    }
  }

  @Override
  protected void postRemoveProcess(Node<E> node) {
    while ((node = node.parent) != null) {
      AvlNode<E> avlNode = AvlNode.cast(node);
      // 平衡
      if (avlNode.isBalanced()) {
        // 更新高度
        avlNode.updateHeight();
      }
      // 不平衡
      else {
        // 恢复平衡
        rebalance(node);
      }
    }
  }

  private void rebalance(Node<E> grandNode) {
    AvlNode<E> grand = AvlNode.cast(grandNode);
    AvlNode<E> parent = grand.tallerChild();
    AvlNode<E> node = parent.tallerChild();
    // L 左子树高
    if (parent.isLeftChild()) {
      // LL 左子树的左子树高
      if (node.isLeftChild()) {
        rotateRight(grand);
      }
      // LR 左子树的右子树高
      else {
        rotateLeft(parent);
        rotateRight(grand);
      }
    }
    // R 右子树高
    else {
      // RR 右子树的右子树高
      if (node.isRightChild()) {
        rotateLeft(grand);
      }
      // RL 右子树的左子树高
      else {
        rotateRight(parent);
        rotateLeft(grand);
      }
    }
  }

  @SuppressWarnings("unused")
  private void rebalance2(Node<E> grandNode) {
    AvlNode<E> grand = AvlNode.cast(grandNode);
    AvlNode<E> parent = grand.tallerChild();
    AvlNode<E> node = parent.tallerChild();
    if (parent.isLeftChild()) {
      // LL
      if (node.isLeftChild()) {
        rotate(grand, node, node.right, parent, parent.right, grand);
      }
      // LR
      else {
        rotate(grand, parent, node.left, node, node.right, grand);
      }
    }
    // R
    else {
      // RR
      if (node.isRightChild()) {
        rotate(grand, grand, parent.left, parent, node.left, node);
      }
      // RL
      else {
        rotate(grand, grand, node.left, node, node.right, parent);
      }
    }
  }

  @Override
  protected void postRotateProcess(Node<E> grand, Node<E> parent, Node<E> child) {
    super.postRotateProcess(grand, parent, child);
    AvlNode.cast(grand).updateHeight();
    AvlNode.cast(parent).updateHeight();
  }

  @Override
  protected void rotate(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
    super.rotate(r, b, c, d, e, f);
    AvlNode.cast(b).updateHeight();
    AvlNode.cast(f).updateHeight();
    AvlNode.cast(d).updateHeight();
  }

  @Override
  protected Node<E> createNode(E element) {
    return AvlNode.create(element);
  }

  @Override
  protected Node<E> createNode(E element, Node<E> parent) {
    return AvlNode.create(element, parent);
  }

  @Data
  @Accessors(chain = true)
  @EqualsAndHashCode(callSuper = true)
  private static class AvlNode<E> extends Node<E> {

    int height;

    int balanceFactor() {
      int leftHeight = leftHeight();
      int rightHeight = rightHeight();

      return leftHeight - rightHeight;
    }

    boolean isBalanced() {
      return Math.abs(this.balanceFactor()) <= 1;
    }

    void updateHeight() {
      int leftHeight = leftHeight();
      int rightHeight = rightHeight();

      this.height = 1 + Math.max(leftHeight, rightHeight);
    }

    int leftHeight() {
      return Objects.isNull(left) ? 0 : AvlNode.cast(left).height;
    }

    int rightHeight() {
      return Objects.isNull(right) ? 0 : AvlNode.cast(right).height;
    }

    AvlNode<E> tallerChild() {
      int leftHeight = leftHeight();
      int rightHeight = rightHeight();

      if (leftHeight > rightHeight) {
        return AvlNode.cast(left);
      }
      if (leftHeight < rightHeight) {
        return AvlNode.cast(right);
      }
      // node 是 parent 哪一边返回哪一边
      return AvlNode.cast(isLeftChild() ? left : right);
    }

    static <E> AvlNode<E> cast(Node<E> node) {
      return TypeSupport.unsafeCast(node);
    }

    static <E> AvlNode<E> create(E element) {
      return create(element, TypeSupport.returnNull());
    }

    static <E> AvlNode<E> create(E element, Node<E> parent) {
      return new AvlNode<E>()
          .setHeight(1)
          .setElement(element)
          .setParent(parent);
    }

    @Override
    public AvlNode<E> setElement(E element) {
      super.setElement(element);
      return this;
    }

    @Override
    public AvlNode<E> setLeft(Node<E> left) {
      super.setLeft(left);
      return this;
    }

    @Override
    public AvlNode<E> setRight(Node<E> right) {
      super.setRight(right);
      return this;
    }

    @Override
    public AvlNode<E> setParent(Node<E> parent) {
      super.setParent(parent);
      return this;
    }

    @Override
    public String toString() {
      return "AvlNode{" +
          "element=" + element +
          '}';
    }

  }


}