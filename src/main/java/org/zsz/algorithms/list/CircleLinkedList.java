package org.zsz.algorithms.list;

import com.google.common.base.Preconditions;
import java.util.Objects;
import lombok.AllArgsConstructor;

/**
 * @author Linus Zhang
 * @create 2022-04-24 23:52
 */
public class CircleLinkedList<E> extends AbstractList<E> {

  protected Node<E> first;

  protected Node<E> last;

  @Override
  public E get(int index) {
    return node(index).element;
  }

  @Override
  public E set(int index, E element) {
    Node<E> node = node(index);
    E old = node.element;
    node.element = element;
    return old;
  }

  @Override
  public void add(int index, E element) {
    rangeCheckForAdd(index);

    // 尾插
    if (index == size) {
      Node<E> prev = last;
      last = new Node<>(prev, element, first);
      if (Objects.isNull(prev)) {
        first = last;
        first.next = first;
        first.prev = first;
      } else {
        prev.next = last;
        first.prev = last;
      }
    } else {
      Node<E> next = node(index);
      Node<E> prev = next.prev;
      Node<E> node = new Node<>(prev, element, next);
      next.prev = node;
      prev.next = node;
      // 头插
      if (index == 0) {
        first = node;
      }
    }

    size++;
  }

  @Override
  public E remove(int index) {
    rangeCheck(index);
    return remove(node(index));
  }

  public E remove(Node<E> node) {
    Preconditions.checkNotNull(node, "CircleLinkedList#remove node is null");
    if (size == 1) {
      node = first;
      first = null;
      last = null;
    } else {
      Node<E> prev = node.prev;
      Node<E> next = node.next;

      prev.next = next;
      next.prev = prev;
      // remove head
      if (node == first) {
        first = next;
      }
      // remove tail
      if (node == last) {
        last = prev;
      }
    }

    size--;
    return node.element;
  }

  @Override
  public int indexOf(E element) {
    Node<E> node = first;
    if (Objects.isNull(element)) {
      for (int i = 0; i < size; i++) {
        if (Objects.isNull(node.element)) {
          return i;
        }
        node = node.next;
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (element.equals(node.element)) {
          return i;
        }
        node = node.next;
      }
    }
    return ELEMENT_NOT_FOUND;
  }

  /**
   * get index Node
   *
   * @param index 索引
   * @return 节点
   */
  private Node<E> node(int index) {
    rangeCheck(index);
    Node<E> curr;
    // from head to index
    if (index < size >> 1) {
      curr = first;
      for (int i = 0; i < index; i++) {
        curr = curr.next;
      }
    }
    // from last to index
    else {
      curr = last;
      for (int i = size - 1; i > index; i--) {
        curr = curr.prev;
      }
    }

    return curr;
  }

  @Override
  public void clear() {
    Node<E> curr = first;
    while (Objects.nonNull(curr)) {
      Node<E> next = curr.next;
      curr.next = null;
      curr.element = null;
      next.prev = null;
      curr = next;
    }
    first = last = null;
    size = 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("LinkedList{");
    sb.append("size=")
        .append(size)
        .append(", elements=[");
    Node<E> curr = first;
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        sb.append(", ");
      }
      sb.append(curr.element);
      curr = curr.next;
    }
    sb.append("]}");
    return sb.toString();
  }

  @AllArgsConstructor
  protected static class Node<E> {

    Node<E> prev;
    E element;
    Node<E> next;

    @Override
    public String toString() {
      return "Node{" +
          "element=" + element +
          '}';
    }
  }
}
