package org.zsz.algorithms.heap;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Linus Zhang
 * @create 2022-07-21 22:39
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHeap<E> implements Heap<E> {

  protected int size;

  protected final Comparator<E> comparator;

  protected AbstractHeap(Comparator<E> comparator) {
    this.comparator = comparator;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return 0 == size;
  }

  protected int compare(E e1, E e2) {
    Function<Comparator<E>, Integer> compare1 = cmp -> cmp.compare(e1, e2);
    Supplier<Integer> compare2 = () -> ((Comparable<E>) e1).compareTo(e2);
    return Optional.ofNullable(comparator)
        .map(compare1)
        .orElseGet(compare2);
  }

}
