package org.zsz.algorithms.queue;

import org.zsz.algorithms.list.LinkedList;

/**
 * 双端队列
 * <p>
 * 链表实现
 *
 * @author Linus Zhang
 * @create 2022-04-26 23:43
 */
public class DequeLinkedImpl<E> implements Deque<E> {

  private final LinkedList<E> list;

  public DequeLinkedImpl() {
    this.list = new LinkedList<>();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public void offerFirst(E element) {
    list.add(0, element);
  }

  @Override
  public void offerLast(E element) {
    list.add(element);
  }

  @Override
  public E pollFirst() {
    return list.remove(0);
  }

  @Override
  public E pollLast() {
    return list.remove(size() - 1);
  }

  @Override
  public E peekFirst() {
    return list.get(0);
  }

  @Override
  public E peekLast() {
    return list.get(size() - 1);
  }

}
