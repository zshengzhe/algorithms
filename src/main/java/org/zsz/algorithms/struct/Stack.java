package org.zsz.algorithms.struct;

import org.zsz.algorithms.list.LinkedList;
import org.zsz.algorithms.list.List;

/**
 * 栈
 *
 * @author Zhang Shengzhe
 * @create 2021-03-29 22:50
 */
public class Stack<E> {

  private final List<E> container;

  public Stack() {
    this.container = new LinkedList<>();
  }

  public void push(E element) {
    container.add(element);
  }

  public E pop() {
    return container.remove(container.size() - 1);
  }

  public E peek() {
    return container.get(container.size() - 1);
  }

  public boolean isEmpty() {
    return container.isEmpty();
  }

  public int size() {
    return container.size();
  }

  public void clear() {
    container.clear();
  }

}