package org.zsz.algorithms.heap;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.zsz.algorithms.support.printer.BinaryTreeInfo;

/**
 * @author Linus Zhang
 * @create 2022-07-21 20:54
 */
@Slf4j
@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

  private E[] elements;

  private static final int DEFAULT_CAPACITY = 10;

  public BinaryHeap() {
    super(null);
    this.elements = (E[]) new Object[DEFAULT_CAPACITY];
  }

  public BinaryHeap(E[] elements) {
    this(elements, null);
  }

  public BinaryHeap(Comparator<E> comparator) {
    super(comparator);
    this.elements = (E[]) new Object[DEFAULT_CAPACITY];
  }

  public BinaryHeap(E[] elements, Comparator<E> comparator) {
    super(comparator);
    this.size = elements.length;
    if (Objects.isNull(elements) || size == 0) {
      this.elements = (E[]) new Object[DEFAULT_CAPACITY];
      return;
    }

    this.elements = (E[]) new Object[Math.max(size, DEFAULT_CAPACITY)];
    System.arraycopy(elements, 0, this.elements, 0, size);
    heapify();
  }

  public static <E> BinaryHeap<E> of(E[] elements) {
    return of(elements, null);
  }

  public static <E> BinaryHeap<E> of(E[] elements, Comparator<E> comparator) {
    return new BinaryHeap<>(elements, comparator);
  }

  private void heapify() {
    // 自上而下的上滤
//    for (int i = 1; i < size; i++) {
//      siftUp(i);
//    }
    // 自下而上的下滤
    for (int i = (size >> 1) - 1; i >= 0; i--) {
      siftDown(i);
    }
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      elements[i] = null;
    }

    size = 0;
  }

  @Override
  public Heap<E> offer(E element) {
    Preconditions.checkNotNull(element, "element is null");
    ensureCapacity(size + 1);
    elements[size++] = element;
    siftUp(size - 1);
    return this;
  }

  @Override
  public E poll() {
    Preconditions.checkState(!isEmpty(), "elements is empty");
    E element = elements[0];

    int index = --size;
    elements[0] = elements[index];
    elements[index] = null;

    siftDown(0);
    return element;
  }

  @Override
  public E replace(E element) {
    Preconditions.checkNotNull(element, "element is null");

    if (isEmpty()) {
      elements[0] = element;
      size++;
      return null;
    }

    E root = elements[0];
    elements[0] = element;
    siftDown(0);
    return root;
  }

  private void siftUp(int index) {
    E element = elements[index];
    while (index > 0) {
      int parentIndex = parentIndex(index);
      E parent = elements[parentIndex];
      if (compare(element, parent) <= 0) {
        break;
      }

      elements[index] = parent;
      index = parentIndex;
    }
    elements[index] = element;
  }

  private void siftDown(int index) {
    E element = elements[index];
    int half = size >> 1;
    // 第一个叶子节点的索引 == 非叶子节点的数量
    while (index < half) {
      Pair<Integer, E> pair = biggerChild(index);
      Integer childIndex = pair.getLeft();
      E child = pair.getRight();

      if (compare(element, child) >= 0) {
        break;
      }

      elements[index] = child;
      index = childIndex;
    }
    elements[index] = element;
  }

  private Pair<Integer, E> biggerChild(int index) {
    int childIndex = leftChildIndex(index);
    E child = elements[childIndex];

    int rightIndex = childIndex + 1;
    if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
      childIndex = rightIndex;
      child = elements[rightIndex];
    }

    return Pair.of(childIndex, child);
  }

  private int parentIndex(int index) {
    return (index - 1) >> 1;
  }

  private int leftChildIndex(int index) {
    return (index << 1) + 1;
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

  @Override
  public Object root() {
    return 0;
  }

  @Override
  public Object left(Object node) {
    int index = ((int) node << 1) + 1;
    return index >= size ? null : index;
  }

  @Override
  public Object right(Object node) {
    int index = ((int) node << 1) + 2;
    return index >= size ? null : index;
  }

  @Override
  public Object string(Object node) {
    return elements[(int) node];
  }

}
