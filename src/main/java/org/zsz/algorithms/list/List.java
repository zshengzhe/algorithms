package org.zsz.algorithms.list;

/**
 * List
 *
 * @author Zhang Shengzhe
 * @create 2020-05-15 13:41
 */
public interface List<E> {

  int ELEMENT_NOT_FOUND = -1;

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
   * add element into list
   *
   * @param element element
   */
  void add(E element);

  /**
   * get element with index
   *
   * @param index index
   * @return element
   */
  E get(int index);

  /**
   * set element with index
   *
   * @param index   index
   * @param element element
   * @return old element
   */
  E set(int index, E element);

  /**
   * add element with index
   *
   * @param index   index
   * @param element element
   */
  void add(int index, E element);

  /**
   * remove element with index
   *
   * @param index index
   * @return old element
   */
  E remove(int index);

  /**
   * get index with element
   *
   * @param element element
   * @return index
   */
  int indexOf(E element);

  /**
   * clear list
   */
  void clear();

}