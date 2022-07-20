package org.zsz.algorithms.map;

import java.util.Objects;
import java.util.function.BiConsumer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.zsz.algorithms.support.TypeSupport;

/**
 * @author Linus Zhang
 * @create 2022-07-19 22:16
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> {

  private LinkedNode<K, V> head;

  private LinkedNode<K, V> tail;

  @Override
  public void clear() {
    super.clear();
    this.head = null;
    this.tail = null;
  }

  @Override
  public void iterate(BiConsumer<K, V> processor) {
    if (Objects.isNull(processor)) {
      return;
    }
    LinkedNode<K, V> node = head;
    while (Objects.nonNull(node)) {
      processor.accept(node.key, node.value);
      node = node.next;
    }
  }

  @Override
  protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
    LinkedNode<K, V> node = LinkedNode.create(key, value, parent);
    if (Objects.isNull(head)) {
      head = tail = node;
    } else {
      tail.next = node;
      node.prev = tail;
      tail = node;
    }
    return node;
  }

  @Override
  protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
    LinkedNode<K, V> will = LinkedNode.cast(willNode);
    LinkedNode<K, V> node = LinkedNode.cast(removedNode);
    if (!Objects.equals(willNode, removedNode)) {
      replace(will, node);
    }

    LinkedNode<K, V> prev = node.prev;
    LinkedNode<K, V> next = node.next;
    if (Objects.isNull(prev)) {
      head = next;
    } else {
      prev.next = next;
    }

    if (Objects.isNull(next)) {
      tail = prev;
    } else {
      next.prev = prev;
    }

    node.prev = null;
    node.next = null;
  }

  private void replace(LinkedNode<K, V> node1, LinkedNode<K, V> node2) {
    LinkedNode<K, V> prev1 = node1.prev;
    LinkedNode<K, V> next1 = node1.next;

    LinkedNode<K, V> prev2 = node2.prev;
    LinkedNode<K, V> next2 = node2.next;

    if (Objects.isNull(prev1)) {
      head = node2;
    } else {
      prev1.next = node2;
    }
    node2.prev = prev1;

    if (Objects.isNull(next1)) {
      tail = node2;
    } else {
      next1.next = node2;
    }
    node2.next = next1;

    if (Objects.isNull(prev2)) {
      head = node1;
    } else {
      prev2.next = node1;
    }
    node1.prev = prev2;

    if (Objects.isNull(next2)) {
      tail = node1;
    } else {
      next2.next = node1;
    }
    node1.prev = next2;
  }

  @Data
  @Accessors(chain = true)
  @EqualsAndHashCode(callSuper = true)
  private static class LinkedNode<K, V> extends Node<K, V> {

    LinkedNode<K, V> prev;

    LinkedNode<K, V> next;

    static <K, V> LinkedNode<K, V> create(K key, V value, Node<K, V> parent) {
      int hash = Objects.hashCode(key);
      return new LinkedNode<K, V>()
          .setHash(hash ^ (hash >>> 16))
          .setKey(key)
          .setValue(value)
          .setColor(RBColor.RED)
          .setParent(parent);
    }

    static <K, V> LinkedNode<K, V> cast(Node<K, V> node) {
      return TypeSupport.unsafeCast(node);
    }

    @Override
    public LinkedNode<K, V> setHash(int hash) {
      super.setHash(hash);
      return this;
    }

    @Override
    public LinkedNode<K, V> setKey(K key) {
      super.setKey(key);
      return this;
    }

    @Override
    public LinkedNode<K, V> setValue(V value) {
      super.setValue(value);
      return this;
    }

    @Override
    public LinkedNode<K, V> setColor(RBColor color) {
      super.setColor(color);
      return this;
    }

    @Override
    public LinkedNode<K, V> setLeft(Node<K, V> left) {
      super.setLeft(left);
      return this;
    }

    @Override
    public LinkedNode<K, V> setRight(Node<K, V> right) {
      super.setRight(right);
      return this;
    }

    @Override
    public LinkedNode<K, V> setParent(Node<K, V> parent) {
      super.setParent(parent);
      return this;
    }

  }

}
