package org.zsz.algorithms.tree;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import org.zsz.algorithms.support.TypeSupport;

/**
 * @author Linus Zhang
 * @create 2022-06-09 22:00
 */
public abstract class BalancedBinarySearchTree<E> extends BinarySearchTree<E> {

  protected BalancedBinarySearchTree() {
    this(TypeSupport.returnNull());
  }

  protected BalancedBinarySearchTree(Comparator<E> comparator) {
    super(comparator);
  }

  protected void rotateLeft(Node<E> grand) {
    Node<E> parent = grand.right;
    Node<E> child = parent.left;

    grand.right = child;
    parent.left = grand;

    postRotateProcess(grand, parent, child);
  }

  protected void rotateRight(Node<E> grand) {
    Node<E> parent = grand.left;
    Node<E> child = parent.right;

    grand.left = child;
    parent.right = grand;

    postRotateProcess(grand, parent, child);
  }

  protected void postRotateProcess(Node<E> grand, Node<E> parent, Node<E> child) {
    Node<E> ancestor = grand.parent;
    // parent成为子树根节点
    parent.parent = ancestor;
    // grand在左
    if (grand.isLeftChild()) {
      ancestor.left = parent;
    }
    // if grand is right child
    else if (grand.isRightChild()) {
      ancestor.right = parent;
    }
    // grand is root
    else {
      root = parent;
    }

    grand.parent = parent;
    if (Objects.nonNull(child)) {
      child.parent = grand;
    }

  }

  protected void rotate(Node<E> r,
      Node<E> b, Node<E> c,
      Node<E> d,
      Node<E> e, Node<E> f) {
    // d成为子树根节点
    d.parent = r.parent;
    if (r.isLeftChild()) {
      r.parent.left = d;
    } else if (r.isRightChild()) {
      r.parent.right = d;
    } else {
      root = d;
    }

    //b-c
    b.right = c;
    Optional.ofNullable(c)
        .ifPresent(n -> n.parent = b);

    // e-f
    f.left = e;
    Optional.ofNullable(e)
        .ifPresent(n -> n.parent = f);

    // b-d-f
    d.left = b;
    d.right = f;
    b.parent = d;
    f.parent = d;
  }

}
