package org.zsz.algorithms.queue;

/**
 * 循环队列
 *
 * @author Linus Zhang
 * @create 2022-04-26 23:55
 */
public class CircleQueue<E> implements Queue<E> {

  private final QueueArrayImpl<E> queue = new QueueArrayImpl<>();

  @Override
  public int size() {
    return queue.size();
  }

  @Override
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  @Override
  public void offer(E element) {
    queue.offer(element);
  }

  @Override
  public E poll() {
    return queue.poll();
  }

  @Override
  public E peek() {
    return queue.peek();
  }

  @Override
  public void clear() {
    queue.clear();
  }

}
