package org.zsz.algorithms.list;

import com.google.common.base.Preconditions;

/**
 * AbstractList
 *
 * @author Zhang Shengzhe
 * @create 2021-03-23 23:02
 */
public abstract class AbstractList<E> implements List<E> {

  protected int size;

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * best O(1) | bad O(n) | average O(1) | O(1)
   *
   * @param element element
   */
  @Override
  public void add(E element) {
    add(size, element);
  }

  @Override
  public boolean contains(E element) {
    return indexOf(element) != ELEMENT_NOT_FOUND;
  }

  protected void rangeCheck(int index) {
//        if (index < 0 || index >= size) {
//            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
//        }
    Preconditions.checkElementIndex(index, size);
  }

  protected void rangeCheckForAdd(int index) {
//        if (index < 0 || index > size) {
//            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
//        }
    Preconditions.checkPositionIndex(index, size);
  }

}