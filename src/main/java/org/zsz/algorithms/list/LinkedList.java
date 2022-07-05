package org.zsz.algorithms.list;

import java.util.Objects;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;

/**
 * @author Linus Zhang
 * @create 2022-04-19 22:51
 */
public class LinkedList<E> extends AbstractList<E> {

  private Node<E> first;

  private Node<E> last;

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
      last = new Node<>(prev, element, null);
      if (Objects.isNull(prev)) {
        first = last;
      } else {
        prev.next = last;
      }
    } else {
      Node<E> next = node(index);
      Node<E> prev = next.prev;
      Node<E> node = new Node<>(prev, element, next);
      next.prev = node;
      if (Objects.nonNull(prev)) {
        prev.next = node;
      }
      // 头插
      else {
        first = node;
      }
    }

    size++;
  }

  @Override
  public E remove(int index) {
    rangeCheck(index);
    Node<E> node = node(index);
    Node<E> prev = node.prev;
    Node<E> next = node.next;
    if (Objects.isNull(prev)) {
      // remove head
      first = next;
    } else {
      prev.next = next;
    }
    if (Objects.isNull(next)) {
      // remove tail
      last = prev;
    } else {
      next.prev = prev;
    }
    node.prev = node.next = null;
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

  public void iterate(Consumer<E> processor) {
    Node<E> curr = first;
    while (Objects.nonNull(curr)) {
      processor.accept(curr.element);
      curr = curr.next;
    }
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
  private static class Node<E> {

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
