package org.zsz.algorithms.set;

import java.util.function.Consumer;

/**
 * @author Linus Zhang
 * @create 2022-07-04 23:38
 */
public interface Set<E> {

  /**
   * get size
   *
   * @return size
   */
  int size();

  /**
   * is empty
   *
   * @return empty -> true
   */
  boolean isEmpty();

  /**
   * contains
   *
   * @param element element
   * @return contains -> true
   */
  boolean contains(E element);

  /**
   * add element into set
   *
   * @param element element
   * @return this
   */
  Set<E> add(E element);

  /**
   * remove element
   *
   * @param element element
   * @return this
   */
  Set<E> remove(E element);

  /**
   * iterate
   *
   * @param processor element processor
   */
  void iterate(Consumer<E> processor);

  /**
   * clear list
   */
  void clear();

}
