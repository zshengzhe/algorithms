package org.zsz.algorithms.queue;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * 队列
 * <p>
 * 使用数组实现
 *
 * @author Linus Zhang
 * @create 2022-04-26 23:51
 */
@Slf4j
@SuppressWarnings("unchecked")
public class QueueArrayImpl<E> implements Queue<E> {

  private int size;

  private E[] elements;

  private int front;

  private static final int DEFAULT_CAPACITY = 10;

  public QueueArrayImpl() {
    this(DEFAULT_CAPACITY);
  }

  public QueueArrayImpl(int capacity) {
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
  public void offer(E element) {
    ensureCapacity(size + 1);
    int index = calculateIndex(size);
    elements[index] = element;
    size++;
  }

  @Override
  public E poll() {
    Preconditions.checkState(!isEmpty(), "Queue is Empty");
    E oldFront = elements[front];
    elements[front] = null;
    front = calculateIndex(1);
    size--;
    return oldFront;
  }

  @Override
  public E peek() {
    return elements[front];
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      elements[calculateIndex(i)] = null;
    }
    this.front = 0;
    this.size = 0;
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
    return (front + offset) % elements.length;
  }

}
