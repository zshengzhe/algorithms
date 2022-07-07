package org.zsz.algorithms.map;

import java.util.function.BiConsumer;

/**
 * @author Linus Zhang
 * @create 2022-07-05 20:51
 */
public interface Map<K, V> {

  int size();

  boolean isEmpty();

  void clear();

  V put(K key, V value);

  V get(K key);

  V remove(K key);

  boolean containsKey(K key);

  boolean containsValue(V value);

  /**
   * iterate
   *
   * @param processor element processor
   */
  void iterate(BiConsumer<K, V> processor);

}
