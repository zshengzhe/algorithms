package org.zsz.algorithms.set;

import java.util.function.Consumer;
import org.zsz.algorithms.list.LinkedList;
import org.zsz.algorithms.list.List;

/**
 * @author Linus Zhang
 * @create 2022-07-04 23:44
 */
public class LinkedListSet<E> implements Set<E> {

  private final LinkedList<E> list = new LinkedList<>();

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(E element) {
    return list.contains(element);
  }

  @Override
  public Set<E> add(E element) {
    int index = list.indexOf(element);
    if (List.ELEMENT_NOT_FOUND == index) {
      list.add(element);
      return this;
    }

    list.set(index, element);
    return this;
  }

  @Override
  public Set<E> remove(E element) {
    int index = list.indexOf(element);
    if (List.ELEMENT_NOT_FOUND == index) {
      return this;
    }

    list.remove(index);
    return this;
  }

  @Override
  public void iterate(Consumer<E> processor) {
    list.iterate(processor);
  }

  @Override
  public void clear() {
    list.clear();
  }

}
