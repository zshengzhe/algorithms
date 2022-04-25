package org.zsz.algorithms.list;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * ArrayList
 *
 * @author Zhang Shengzhe
 * @create 2020-12-25 16:15
 */
@Slf4j
@SuppressWarnings("unchecked")
public class ArrayList<E> extends AbstractList<E> {

  private E[] elements;

  private static final int DEFAULT_CAPACITY = 10;

  public ArrayList() {
    this(DEFAULT_CAPACITY);
  }

  public ArrayList(int capacity) {
    capacity = Math.max(capacity, DEFAULT_CAPACITY);
    this.elements = (E[]) new Object[capacity];
    this.size = 0;
  }

  /**
   * O(1)
   *
   * @param index 索引
   * @return 元素
   */
  @Override
  public E get(int index) {
    rangeCheck(index);
    return elements[index];
  }

  /**
   * O(1)
   *
   * @param index   索引
   * @param element 元素
   * @return 旧元素
   */
  @Override
  public E set(int index, E element) {
    rangeCheck(index);
    E old = elements[index];
    elements[index] = element;
    return old;
  }

  /**
   * best O(1) | bad O(n) | average O(n/2) = O(n)
   *
   * @param index   索引
   * @param element 元素
   */
  @Override
  public void add(int index, E element) {
    rangeCheckForAdd(index);
    ensureCapacity(size + 1);
    // 从右向左循环，将元素逐个向右挪1位
    for (int i = size; i > index; i--) {
      elements[i] = elements[i - 1];
    }
    // 插入新元素
    elements[index] = element;
    size++;
  }

  /**
   * best O(1) | bad O(n) | average O(n/2) = O(n)
   *
   * @param index 索引
   * @return 旧元素
   */
  @Override
  public E remove(int index) {
    rangeCheck(index);
    E oldElement = get(index);
    for (; index < size - 1; index++) {
      elements[index] = elements[index + 1];
    }
    // clear to let GC do its work
    elements[--size] = null;

    trimToSize();

    return oldElement;
  }

  public boolean remove(E element) {
    int index = indexOf(element);
    if (index == ELEMENT_NOT_FOUND) {
      return false;
    }
    remove(index);
    return true;
  }

  @Override
  public int indexOf(E element) {
    if (Objects.isNull(element)) {
      for (int i = 0; i < size; i++) {
        if (Objects.isNull(elements[i])) {
          return i;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (element.equals(elements[i])) {
          return i;
        }
      }
    }
    return ELEMENT_NOT_FOUND;
  }

  @Override
  public void clear() {
    // clear to let GC do its work
    for (int i = 0; i < size; i++) {
      elements[i] = null;
    }
    size = 0;
  }

  private void ensureCapacity(int capacity) {
    int oldCapacity = elements.length;
    if (oldCapacity > capacity) {
      return;
    }
    // 1.5
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    E[] newElements = (E[]) new Object[newCapacity];
    System.arraycopy(elements, 0, newElements, 0, size);
    elements = newElements;
    log.info("change capacity: {} -> {}", oldCapacity, newCapacity);
  }

  /**
   * 缩容
   */
  private void trimToSize() {
    int oldCapacity = elements.length;
    // size < (capacity / 2) 元素小于一半 且 容量大于默认容量进行缩容
    if (size > (oldCapacity >> 1) && oldCapacity <= DEFAULT_CAPACITY) {
      return;
    }
    int newCapacity = oldCapacity >> 1;
    E[] newElements = (E[]) new Object[newCapacity];
    System.arraycopy(elements, 0, newElements, 0, size);
    elements = newElements;
    log.info("change capacity: {} -> {}", oldCapacity, newCapacity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ArrayList{");
    sb.append("size=")
        .append(size)
        .append(", elements=[");
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        sb.append(", ");
      }
      sb.append(elements[i]);
    }
    sb.append("]}");
    return sb.toString();
  }

}