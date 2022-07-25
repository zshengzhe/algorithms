package org.zsz.algorithms.heap;

/**
 * @author Linus Zhang
 * @create 2022-07-21 20:34
 */
public interface Heap<E> {

  int size();

  boolean isEmpty();

  void clear();

  Heap<E> offer(E element);

  E poll();

  E replace(E element);

}
