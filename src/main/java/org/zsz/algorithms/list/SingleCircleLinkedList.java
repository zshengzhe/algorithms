package org.zsz.algorithms.list;

import java.util.Objects;
import lombok.AllArgsConstructor;

/**
 * @author Linus Zhang
 * @create 2022-04-24 23:36
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {

  private Node<E> first;

  @Override
  public boolean contains(E element) {
    return indexOf(element) != ELEMENT_NOT_FOUND;
  }

  @Override
  public E get(int index) {
    return node(index).element;
  }

  @Override
  public E set(int index, E element) {
    Node<E> node = node(index);
    E oldElement = node.element;
    node.element = element;
    return oldElement;
  }

  @Override
  public void add(int index, E element) {
    rangeCheckForAdd(index);
    if (index == 0) {
      Node<E> newFirst = new Node<>(element, first);
      // 拿到最后一个节点
      Node<E> last = size == 0 ? newFirst : node(size - 1);
      first = newFirst;
      last.next = first;
    } else {
      Node<E> prev = node(index - 1);
      prev.next = new Node<>(element, prev.next);
    }
    size++;
  }

  @Override
  public E remove(int index) {
    Node<E> node = first;
    if (index == 0) {
      rangeCheck(index);
      if (size == 1) {
        first = null;
      } else {
        Node<E> last = node(size - 1);
        first = first.next;
        last.next = first;
      }
    } else {
      Node<E> prev = node(index - 1);
      node = prev.next;
      prev.next = node.next;
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
    Node<E> curr = first;
    for (int i = 0; i < index; i++) {
      curr = curr.next;
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
      curr = next;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("LinkedList{");
    sb.append("size=")
        .append(size)
        .append(", elements=[");
    Node<E> node = first;
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        sb.append(", ");
      }
      sb.append(node.element);
      node = node.next;
    }
    sb.append("]}");
    return sb.toString();
  }

  @AllArgsConstructor
  private static class Node<E> {

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
