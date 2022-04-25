package org.zsz.algorithms.list;

/**
 * @author Zhang Shengzhe
 * @create 2021-03-29 00:01
 */
public class CircleLinkedListV2<E> extends CircleLinkedList<E> {

  private Node<E> current;

  public void reset() {
    current = first;
  }

  public E next() {
    if (current == null) {
      return null;
    }
    current = current.next;
    return current.element;
  }

  public E remove() {
    if (current == null) {
      return null;
    }
    Node<E> next = size == 1 ? null : current.next;
    E remove = remove(current);
    current = next;
    return remove;
  }

}