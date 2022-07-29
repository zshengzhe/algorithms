package org.zsz.algorithms.tree;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.zsz.algorithms.support.TypeSupport;

/**
 * @author Linus Zhang
 * @create 2022-07-29 22:31
 */
public class TrieImpl<T> implements Trie<T> {

  private int size;

  private final Node<T> root = new Node<>(null);

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return 0 == size;
  }

  @Override
  public void clear() {
    if (isEmpty()) {
      return;
    }

    Queue<Node<T>> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      Node<T> node = queue.poll();
      node.parent = null;
      node.key = null;
      node.value = null;

      Consumer<Map<Character, Node<T>>> setQueue = children -> children.values().forEach(queue::offer);
      Optional.ofNullable(node.children)
          .ifPresent(setQueue);
      node.children = null;
    }
    size = 0;
  }

  @Override
  public boolean contains(String str) {
    Node<T> node = node(requiredString(str));
    return Optional.ofNullable(node)
        .map(Node::isWord)
        .orElse(false);
  }

  @Override
  public T add(String str, T value) {
    char[] chars = requiredString(str).toCharArray();
    Node<T> node = root;

    int length = chars.length;
    for (int i = 0; i < length; i++) {
      char key = chars[i];
      // last char
      if (i == length - 1) {
        Pair<Node<T>, Node<T>> newAndOld = node.addChildren(key, value, true);
        size++;
        return Optional.ofNullable(newAndOld.getValue())
            .map(Node::getValue)
            .orElseGet(TypeSupport::returnNull);

      }

      node = node.containsKey(key) ? node.children.get(key) : node.addChildren(key, value, false).getKey();
    }

    return null;
  }

  @Override
  public T remove(String str) {
    Node<T> node = node(requiredString(str));
    if (Objects.isNull(node)) {
      return null;
    }

    T value = node.getValue();
    size--;
    if (MapUtils.isNotEmpty(node.children)) {
      node.word = false;
      return value;
    }

    while (!Objects.equals(node, root)) {
      Node<T> parent = node.parent;
      parent.children.remove(node.key);
      if (parent.childrenHasWord()) {
        break;
      }
      node = parent;
    }
    return value;
  }

  @Override
  public T get(String str) {
    Node<T> node = node(requiredString(str));
    return Optional.ofNullable(node)
        .map(nd -> nd.word ? node.value : null)
        .orElseGet(TypeSupport::returnNull);
  }

  @Override
  public boolean startsWith(String prefix) {
    Node<T> node = node(requiredString(prefix));
    return Objects.nonNull(node);
  }

  private Node<T> node(String str) {
    char[] chars = str.toCharArray();
    Node<T> node = root;

    for (char key : chars) {
      if (Objects.isNull(node)) {
        return null;
      }

      if (MapUtils.isEmpty(node.children)) {
        return node;
      }

      node = node.children.get(key);

    }

    return node;
  }

  private String requiredString(String str) {
    Preconditions.checkState(StringUtils.isNotBlank(str), "str is null");
    return str;
  }

  @Data
  @Accessors(chain = true)
  private static class Node<T> {

    Node<T> parent;

    Map<Character, Node<T>> children;

    Character key;

    T value;

    boolean word;

    Node(Node<T> parent) {
      this.parent = parent;
    }

    boolean containsKey(char key) {
      if (MapUtils.isEmpty(children)) {
        return false;
      }

      return children.containsKey(key);
    }

    Pair<Node<T>, Node<T>> addChildren(Character key, T value, boolean word) {
      if (Objects.isNull(children)) {
        this.children = Maps.newHashMap();
      }
      Node<T> newNode = new Node<>(this).setKey(key).setValue(value).setWord(word);
      return Pair.of(newNode, children.put(key, newNode));
    }

    boolean childrenHasWord() {
      Queue<Node<T>> queue = new LinkedList<>();

      queue.offer(this);
      while (!queue.isEmpty()) {
        Node<T> node = queue.poll();

        if (node.word) {
          return true;
        }

        Consumer<Map<Character, Node<T>>> setQueue = children -> children.values().forEach(queue::offer);
        Optional.ofNullable(node.children)
            .ifPresent(setQueue);
      }
      return false;
    }

    @Override
    public String toString() {
      return "Node{" +
          "key=" + key +
          ", children=" + children +
          '}';
    }

  }

}
