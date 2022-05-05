package org.zsz.algorithms.queue;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * 双端队列
 * <p>
 * 数组实现
 *
 * @author Linus Zhang
 * @create 2022-04-28 00:25
 */
@Slf4j
@SuppressWarnings("unchecked")
public class DequeArrayImpl<E> implements Deque<E> {

  private int size;

  private E[] elements;

  private int front;

  private static final int DEFAULT_CAPACITY = 10;

  public DequeArrayImpl() {
    this(DEFAULT_CAPACITY);
  }

  public DequeArrayImpl(int capacity) {
    capacity = Math.max(capacity, DEFAULT_CAPACITY);
    this.elements = (E[]) new Object[capacity];
    this.size = 0;
    this.front = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      int index = (front + i) % elements.length;
      elements[index] = null;
    }
    this.size = 0;
    this.front = 0;
  }

  @Override
  public void offerFirst(E element) {
    ensureCapacity(size + 1);
    this.front = calculateIndex(-1);
    elements[front] = element;
    size++;
  }

  @Override
  public void offerLast(E element) {
    ensureCapacity(size + 1);
    int index = calculateIndex(size);
    elements[index] = element;
    size++;
  }

  @Override
  public E pollFirst() {
    Preconditions.checkState(!isEmpty(), "Queue is Empty");
    E oldFront = elements[front];
    elements[front] = null;
    front = calculateIndex(1);
    size--;
    return oldFront;
  }

  @Override
  public E pollLast() {
    Preconditions.checkState(!isEmpty(), "Queue is Empty");
    int index = calculateIndex(size - 1);
    E oldLast = elements[index];
    elements[index] = null;
    size--;
    return oldLast;
  }

  @Override
  public E peekFirst() {
    return elements[front];
  }

  @Override
  public E peekLast() {
    return elements[calculateIndex(size - 1)];
  }

  private void ensureCapacity(int capacity) {
    if (capacity > elements.length) {
      int newCapacity = elements.length + (elements.length >> 1);
      E[] newElements = (E[]) new Object[newCapacity];
      for (int i = 0; i < size; i++) {
        int index = calculateIndex(i);
        newElements[i] = elements[index];
      }
      log.info("change capacity: {} -> {}", elements.length, newCapacity);
      elements = newElements;
      this.front = 0;
    }
  }

  private int calculateIndex(int offset) {
    int tmp = front + offset;
    return tmp < 0 ? tmp + elements.length : tmp % elements.length;
  }

}
