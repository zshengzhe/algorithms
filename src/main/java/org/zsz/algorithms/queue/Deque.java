package org.zsz.algorithms.queue;

/**
 * 双端队列
 *
 * @author Zhang Shengzhe
 * @create 2021-03-31 22:19
 */
public interface Deque<E> {

  int size();

  boolean isEmpty();

  void clear();

  void offerFirst(E element);

  void offerLast(E element);

  E pollFirst();

  E pollLast();

  E peekFirst();

  E peekLast();

}