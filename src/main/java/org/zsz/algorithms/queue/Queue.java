package org.zsz.algorithms.queue;

/**
 * 队列
 *
 * @author Zhang Shengzhe
 * @create 2021-03-29 23:29
 */
public interface Queue<E> {

  int size();

  boolean isEmpty();

  /**
   * 入队
   *
   * @param element element
   */
  void offer(E element);

  /**
   * get and remove head element
   *
   * @return element
   */
  E poll();

  /**
   * 获取队列头的元素
   *
   * @return element
   */
  E peek();

  /**
   * clear queue
   */
  void clear();

}