package org.zsz.algorithms.set;

import java.util.function.Consumer;
import org.zsz.algorithms.tree.RedBlackTree;

/**
 * @author Linus Zhang
 * @create 2022-07-04 23:57
 */
public class RedBlackTreeSet<E> implements Set<E> {

  private final RedBlackTree<E> tree = new RedBlackTree<>();

  @Override
  public int size() {
    return tree.size();
  }

  @Override
  public boolean isEmpty() {
    return tree.isEmpty();
  }

  @Override
  public boolean contains(E element) {
    return tree.contains(element);
  }

  @Override
  public Set<E> add(E element) {
    tree.add(element);
    return this;
  }

  @Override
  public Set<E> remove(E element) {
    tree.remove(element);
    return this;
  }

  @Override
  public void iterate(Consumer<E> processor) {
    tree.inorder(processor);
  }

  @Override
  public void clear() {
    tree.clear();
  }

}
