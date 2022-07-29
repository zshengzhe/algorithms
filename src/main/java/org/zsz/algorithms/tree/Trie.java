package org.zsz.algorithms.tree;

/**
 * @author Linus Zhang
 * @create 2022-07-29 22:07
 */
public interface Trie<T> {

  int size();

  boolean isEmpty();

  void clear();

  boolean contains(String str);

  T add(String str, T value);

  T remove(String str);

  T get(String str);

  boolean startsWith(String prefix);

}
