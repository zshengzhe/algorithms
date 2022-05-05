package org.zsz.algorithms.queue;

import org.zsz.algorithms.list.LinkedList;

/**
 * 队列
 *
 * @author Linus Zhang
 * @create 2022-04-26 23:23
 */
public class QueueImpl<E> implements Queue<E> {

  private final LinkedList<E> list;

  public QueueImpl() {
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
  public void offer(E element) {
    list.add(element);
  }

  @Override
  public E poll() {
    return list.remove(0);
  }

  @Override
  public E peek() {
    return list.get(0);
  }

  @Override
  public void clear() {
    list.clear();
  }

}
