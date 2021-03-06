package org.zsz.algorithms.map;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import lombok.Data;
import lombok.experimental.Accessors;
import org.zsz.algorithms.support.TypeSupport;

/**
 * @author Linus Zhang
 * @create 2022-07-05 21:07
 */
@SuppressWarnings("unchecked")
public class TreeMap<K, V> implements Map<K, V> {

  private final Comparator<K> comparator;

  private int size;

  private Node<K, V> root;

  public TreeMap() {
    this(null);
  }

  public TreeMap(Comparator<K> comparator) {
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

  @Override
  public void clear() {
    size = 0;

    if (Objects.isNull(root)) {
      return;
    }

    Queue<Node<K, V>> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      Node<K, V> node = queue.poll();

      Optional.ofNullable(node.left)
          .ifPresent(queue::offer);

      Optional.ofNullable(node.right)
          .ifPresent(queue::offer);

      // clear help gc
      node.parent = null;
      node.key = null;
      node.value = null;
      node.left = null;
      node.right = null;
    }
    root = null;
  }

  @Override
  public V put(K key, V value) {
    requireKeyNonNull(key);

    if (Objects.isNull(root)) {
      root = Node.create(key, value);
      size++;
      postPutProcess(root);
      return TypeSupport.returnNull();
    }

    Node<K, V> parent = root;
    Node<K, V> node = root;
    int cmp = 0;
    while (Objects.nonNull(node)) {
      parent = node;

      cmp = compare(key, node.key);
      if (cmp == 0) {
        node.key = key;
        V oldValue = node.value;
        node.value = value;
        return oldValue;
      }

      node = cmp > 0 ? node.right : node.left;
    }

    Node<K, V> newNode = Node.create(key, value, parent);
    if (cmp > 0) {
      parent.right = newNode;
    } else {
      parent.left = newNode;
    }
    size++;
    postPutProcess(newNode);
    return TypeSupport.returnNull();
  }

  @Override
  public V get(K key) {
    return node(key)
        .map(Node::getValue)
        .orElseGet(TypeSupport::returnNull);
  }

  @Override
  public V remove(K key) {
    return node(key)
        .map(this::remove)
        .orElseGet(TypeSupport::returnNull);
  }

  @Override
  public boolean containsKey(K key) {
    return node(key).isPresent();
  }

  @Override
  public boolean containsValue(V value) {
    if (isEmpty()) {
      return false;
    }

    Queue<Node<K, V>> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      Node<K, V> node = queue.poll();

      if (Objects.equals(value, node.value)) {
        return true;
      }

      Optional.ofNullable(node.left)
          .ifPresent(queue::offer);

      Optional.ofNullable(node.right)
          .ifPresent(queue::offer);
    }
    return false;
  }

  @Override
  public void iterate(BiConsumer<K, V> processor) {
    if (Objects.isNull(processor) || isEmpty()) {
      return;
    }
    inorder(root, processor);
  }

  private Optional<Node<K, V>> node(K key) {
    Node<K, V> node = root;
    while (Objects.nonNull(node)) {
      int cmp = compare(key, node.key);
      if (cmp == 0) {
        return Optional.of(node);
      }

      node = cmp > 0 ? node.right : node.left;
    }
    return Optional.empty();
  }

  private void inorder(Node<K, V> node, BiConsumer<K, V> processor) {
    if (Objects.isNull(node)) {
      return;
    }
    inorder(node.left, processor);
    processor.accept(node.key, node.value);
    inorder(node.right, processor);
  }

  private void postPutProcess(Node<K, V> node) {
    Node<K, V> parent = node.parent;
    // ??????????????????
    if (Objects.isNull(parent)) {
      Node.black(node);
      return;
    }

    /*
     * ?????????????????? ??????????????????B?????????
     *
     * R(sibling)-B(Parent)-node
     */
    if (Node.isBlack(parent)) {
      return;
    }

    // ????????????
    Node<K, V> uncle = parent.sibling();
    // ???????????? ??????
    Node<K, V> grand = Node.red(parent.parent);

    /*
     * ?????????????????????
     *
     *                       -- R ---- B ---- R
     *                      |
     *    -- R(Parent) ---- B(Grand) ---- R(Uncle)
     *   |         ???????????????????????????????????????????????????????????????B?????????
     * node        ?????????????????????????????? ????????????B?????????
     *
     *          -- R(Grand) --
     *         |              |
     * R(node)-B(Parent)     B(Uncle)
     */
    if (Node.isRed(uncle)) {
      Node.black(parent);
      Node.black(uncle);
      // ???????????????????????????????????????
      postPutProcess(grand);
      return;
    }

    /*
     * ?????????????????????
     * ??????????????????????????????B????????????????????????
     *
     *   R(Parent)-B(Grand)
     *  |
     * node
     *
     * 1.??????G 2.??????B
     *
     * R(Grand)-B(Parent)-R(node)
     * --------------------------
     *
     *  R(Parent) --B(Grand)
     *         |
     *        node
     *
     * 1.??????G 2.??????node
     *
     * R(Grand)-B(node)-R(Parent)
     * --------------------------
     *
     *                           -- R ---- B ---- R
     *                          |
     *        -- R(Parent) ---- B(Grand)
     *       |   |
     *     node node LR (1.?????????????????? 2.?????????????????????)
     * LL (1.???????????????)
     */
    // L ???????????????
    if (parent.isLeftChild()) {
      // LL node??????
      if (node.isLeftChild()) {
        Node.black(parent);
      }
      // LR node??????
      else {
        Node.black(node);
        rotateLeft(parent);
      }
      rotateRight(grand);
    }
    // R ???????????????
    else {
      // RR node??????
      if (node.isRightChild()) {
        Node.black(parent);
      }
      // RL node??????
      else {
        Node.black(node);
        rotateRight(parent);
      }
      rotateLeft(grand);
    }
  }

  private V remove(Node<K, V> node) {
    V oldValue = node.value;

    // ??????2
    if (node.degreeTwo()) {
      Node<K, V> successor = successor(node);
      Preconditions.checkNotNull(successor);
      node.key = successor.key;
      node.value = successor.value;
      node = successor;
    }
    Node<K, V> parent = node.parent;

    // ??????2???0
    Node<K, V> child = Optional.ofNullable(node.left)
        .orElse(node.right);
    // ??????1 ?????????????????????
    if (Objects.nonNull(child)) {
      child.parent = parent;
      if (Objects.isNull(parent)) {
        root = child;
      }
      // child??????????????????????????????
      else if (node.isLeftChild()) {
        parent.left = child;
      } else {
        parent.right = child;
      }

      postRemoveProcess(child);
    }
    // ????????? ??????0
    else if (Objects.isNull(parent)) {
      root = null;

      postRemoveProcess(node);
    }
    // ????????????
    else {
      // child??????????????????????????????
      if (node.isLeftChild()) {
        parent.left = null;
      } else {
        parent.right = null;
      }

      postRemoveProcess(node);
    }

    size--;

    return oldValue;
  }

  private void postRemoveProcess(Node<K, V> node) {
    /*
     * ??????: ?????????????????????????????????????????????
     * ????????????replacement????????????,???replacement??????node???????????????
     *  -- R ---- B
     * |
     * B(node) -- R(replacement)
     */
    if (Node.isRed(node)) {
      Node.black(node);
      return;
    }

    // root
    if (node.isRoot()) {
      return;
    }

    // ????????????????????????
    Node<K, V> parent = node.parent;
    boolean left = Objects.isNull(parent.left) || node.isLeftChild();
    Node<K, V> sibling = left ? parent.right : parent.left;

    if (left) {
      /*
       * ???????????????
       * node??????
       * 1.sibling?????? parent??????
       * 2.parent ?????????
       *
       * B(parent) --> R(sibling) --
       * |             |            |
       * v             v            v
       * B(node)       B            B
       * ------------------------------------
       *  ------------ R(parent) <-- B(sibling) --
       * |             |                          |
       * v             v                          v
       * B(node)       B                          B
       */
      if (Node.isRed(sibling)) {
        Node.black(sibling);
        Node.red(parent);
        rotateLeft(parent);
        sibling = parent.right;
      }
      if (Node.isBlack(sibling.left) && Node.isBlack(sibling.right)) {
        RBColor parentColor = Node.colorOf(parent);
        Node.black(parent);
        Node.red(sibling);
        if (RBColor.BLACK == parentColor) {
          postRemoveProcess(parent);
        }
      } else {
        if (Node.isBlack(sibling.right)) {
          rotateRight(sibling);
          sibling = parent.right;
        }
        Node.dyeing(sibling, Node.colorOf(parent));
        Node.black(sibling.right);
        Node.black(parent);
        rotateLeft(parent);

      }
    } else {
      /*
       * ???????????????
       * node??????
       * 1.sibling?????? parent??????
       * 2.parent ?????????
       *
       *  -- R(sibling) <-- B(parent) --
       * |       |                      |
       * v       v                      v
       * B       B                      B(node)
       * ------------------------------------
       *  -- B(sibling) --> R(parent) ---
       * |                  |            |
       * v                  v            v
       * B                  B            B(node)
       */
      if (Node.isRed(sibling)) {
        Node.black(sibling);
        Node.red(parent);
        rotateRight(parent);
        /*
         * ????????????
         *  --B(parent)--> R(sibling) -----
         * |               |               |
         * v               v               v
         * B               B(new sibling)  B(node)
         */
        sibling = parent.left;
      }
      /*
       * ?????????????????? ?????????????????????
       * sibling?????? parent??????
       * B --> R(parent) ---
       *       |            |
       *       v            v
       *       B(sibling)   B(node)
       * ----------------------------------------
       * B --> B(parent)      B ----------------
       * |     |          =>  |                 |
       * v     v              v                 v
       * B     R(sibling)     B     R(sibling)--B(parent)
       */
      if (Node.isBlack(sibling.left) && Node.isBlack(sibling.right)) {
        RBColor parentColor = Node.colorOf(parent);
        Node.black(parent);
        Node.red(sibling);
        /*
         * ???????????? ??????????????????
         * ????????????
         * B(parent) --                    ??? --
         * |           |        =>              |
         * v           v                        v
         * B           B(node)      R(sibling)--B(parent)
         */
        if (RBColor.BLACK == parentColor) {
          postRemoveProcess(parent);
        }
      }
      /*
       * B????????????????????????
       * node??????
       * 1.?????????sibling??????parent????????????(LL - LR)
       * 2.???sibling??????parent?????????
       * 3.???sibling????????????
       *    B --> R(parent) -----------
       *          |                    |
       *          v                    v
       * R(?????????)-B(sibling)-R(?????????)   B(node)
       * --------------------------------------
       *    B --> R(sibling) --
       *          |            |
       *          v            v
       *          B            B(parent)
       */
      else {
        if (Node.isBlack(sibling.left)) {
          rotateLeft(sibling);
          sibling = parent.left;
        }
        Node.dyeing(sibling, Node.colorOf(parent));
        Node.black(sibling.left);
        Node.black(parent);
        rotateRight(parent);

      }
    }
  }

  private void rotateLeft(Node<K, V> grand) {
    Node<K, V> parent = grand.right;
    Node<K, V> child = parent.left;

    grand.right = child;
    parent.left = grand;

    postRotateProcess(grand, parent, child);
  }

  private void rotateRight(Node<K, V> grand) {
    Node<K, V> parent = grand.left;
    Node<K, V> child = parent.right;

    grand.left = child;
    parent.right = grand;

    postRotateProcess(grand, parent, child);
  }

  private void postRotateProcess(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
    Node<K, V> ancestor = grand.parent;
    // parent?????????????????????
    parent.parent = ancestor;
    // grand??????
    if (grand.isLeftChild()) {
      ancestor.left = parent;
    }
    // if grand is right child
    else if (grand.isRightChild()) {
      ancestor.right = parent;
    }
    // grand is root
    else {
      root = parent;
    }

    grand.parent = parent;
    if (Objects.nonNull(child)) {
      child.parent = grand;
    }

  }

  private K requireKeyNonNull(K key) {
    return Objects.requireNonNull(key, "key must not null");
  }

  private int compare(K key1, K key2) {
    Supplier<Integer> compare = () -> ((Comparable<K>) key1).compareTo(key2);
    return Optional.ofNullable(comparator)
        .map(cmp -> cmp.compare(key1, key2))
        .orElseGet(compare);
  }

  /**
   * ???????????? <br/> ???????????????????????????
   *
   * @param node ??????
   * @return ????????????
   */
  private Node<K, V> successor(Node<K, V> node) {
    if (Objects.isNull(node)) {
      return null;
    }

    // R-L-L-L-L
    if (Objects.nonNull(node.right)) {
      Node<K, V> successor = node.right;
      while (Objects.nonNull(successor.left)) {
        successor = successor.left;
      }
      return successor;
    }

    // node????????????????????????
    while (Objects.nonNull(node.parent) && Objects.equals(node, node.parent.right)) {
      node = node.parent;
    }
    return node.parent;
  }

  @Data
  @Accessors(chain = true)
  private static class Node<K, V> {

    K key;

    V value;

    RBColor color;

    Node<K, V> left;

    Node<K, V> right;

    Node<K, V> parent;

    static <K, V> Node<K, V> create(K key, V value, Node<K, V> parent) {
      return new Node<K, V>()
          .setKey(key)
          .setValue(value)
          .setParent(parent);
    }

    static <K, V> Node<K, V> create(K key, V value) {
      return create(key, value, TypeSupport.returnNull());
    }

    static <K, V> Node<K, V> red(Node<K, V> node) {
      return dyeing(node, RBColor.RED);
    }

    static <K, V> Node<K, V> black(Node<K, V> node) {
      return dyeing(node, RBColor.BLACK);
    }

    static <K, V> Node<K, V> dyeing(Node<K, V> node, RBColor color) {
      if (Objects.isNull(node)) {
        return TypeSupport.returnNull();
      }

      node.color = color;

      return node;
    }

    static <K, V> RBColor colorOf(Node<K, V> node) {
      return Optional.ofNullable(node)
          .map(Node::getColor)
          .orElse(RBColor.BLACK);
    }

    static <K, V> boolean isBlack(Node<K, V> node) {
      return colorOf(node) == RBColor.BLACK;
    }

    static <K, V> boolean isRed(Node<K, V> node) {
      return colorOf(node) == RBColor.RED;
    }

    boolean isLeaf() {
      return Objects.isNull(left) && Objects.isNull(right);
    }

    boolean isNotLeaf() {
      return !isLeaf();
    }

    boolean degreeTwo() {
      return Objects.nonNull(left) && Objects.nonNull(right);
    }

    boolean isLeftChild() {
      return Objects.nonNull(parent) && this == parent.left;
    }

    boolean isRightChild() {
      return Objects.nonNull(parent) && this == parent.right;
    }

    boolean isRoot() {
      return Objects.isNull(parent);
    }

    Node<K, V> sibling() {
      if (isLeftChild()) {
        return parent.right;
      } else if (isRightChild()) {
        return parent.left;
      }
      return TypeSupport.returnNull();
    }

    @Override
    public String toString() {
      return String.format("Node:{key = %s, value = %s}", key, value);
    }

  }

  private enum RBColor {
    /**
     * ???
     */
    RED,
    /**
     * ???
     */
    BLACK
  }

}
