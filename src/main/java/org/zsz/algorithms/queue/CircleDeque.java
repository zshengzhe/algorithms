package org.zsz.algorithms.queue;

/**
 * 循环双端队列
 *
 * @author Linus Zhang
 * @create 2022-04-28 00:24
 */
public class CircleDeque<E> implements Deque<E> {

  private final DequeArrayImpl<E> deque = new DequeArrayImpl<>();

  @Override
  public int size() {
    return deque.size();
  }

  @Override
  public boolean isEmpty() {
    return deque.isEmpty();
  }

  @Override
  public void clear() {
    deque.clear();
  }

  @Override
  public void offerFirst(E element) {
    deque.offerFirst(element);
  }

  @Override
  public void offerLast(E element) {
    deque.offerLast(element);
  }

  @Override
  public E pollFirst() {
    return deque.pollFirst();
  }

  @Override
  public E pollLast() {
    return deque.pollLast();
  }

  @Override
  public E peekFirst() {
    return deque.peekFirst();
  }

  @Override
  public E peekLast() {
    return deque.peekLast();
  }
}
