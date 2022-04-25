package org.zsz.algorithms.list;

import java.util.Objects;
import lombok.AllArgsConstructor;

/**
 * @author Linus Zhang
 * @create 2022-04-19 00:19
 */
public class SingleLinkedListV2<E> extends AbstractList<E> {

  private final Node<E> head;

  public SingleLinkedListV2() {
    head = new Node<>(null, null);
  }

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
    Node<E> prev = index == 0 ? head : node(index - 1);
    prev.next = new Node<>(element, prev.next);
    size++;
  }

  @Override
  public E remove(int index) {
    Node<E> prev = index == 0 ? head : node(index - 1);
    Node<E> node = prev.next;
    prev.next = node.next;
    size--;
    return node.element;
  }

  @Override
  public int indexOf(E element) {
    Node<E> node = head.next;
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
    Node<E> curr = head.next;
    for (int i = 0; i < index; i++) {
      curr = curr.next;
    }
    return curr;
  }

  @Override
  public void clear() {
    Node<E> curr = head;
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
    Node<E> node = head.next;
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
  }


}
