package org.zsz.algorithms.set;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.zsz.algorithms.map.TreeMap;

/**
 * @author Linus Zhang
 * @create 2022-07-07 22:43
 */
public class TreeSet<E> implements Set<E> {

  private static final Object PRESENT = new Object();

  private final TreeMap<E, Object> map;

  public TreeSet() {
    this(null);
  }

  public TreeSet(Comparator<E> comparator) {
    map = new TreeMap<>(comparator);
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean contains(E element) {
    return map.containsKey(element);
  }

  @Override
  public Set<E> add(E element) {
    map.put(element, PRESENT);
    return this;
  }

  @Override
  public Set<E> remove(E element) {
    map.remove(element);
    return this;
  }

  @Override
  public void iterate(Consumer<E> processor) {
    BiConsumer<E, Object> keyProcessor = (k, v) -> processor.accept(k);
    map.iterate(keyProcessor);
  }

  @Override
  public void clear() {
    map.clear();
  }

}
