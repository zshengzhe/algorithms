package org.zsz.algorithms.map;

import com.google.common.base.Preconditions;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiConsumer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.zsz.algorithms.support.TypeSupport;
import org.zsz.algorithms.support.printer.BinaryTreeInfo;
import org.zsz.algorithms.support.printer.BinaryTrees;

/**
 * 哈希表
 *
 * @author Linus Zhang
 * @create 2022-07-11 21:29
 */
@SuppressWarnings("unchecked")
public class HashMap<K, V> implements Map<K, V> {

  private static final int DEFAULT_TABLE_SIZE = 16;

  private static final float DEFAULT_LOAD_FACTOR = 0.75f;

  private int size;

  private Node<K, V>[] table;

  private final float loadFactor;

  public HashMap() {
    this(DEFAULT_TABLE_SIZE);
  }

  public HashMap(int size) {
    loadFactor = DEFAULT_LOAD_FACTOR;
    table = new Node[Math.max(size, DEFAULT_TABLE_SIZE)];
  }

  public HashMap(float loadFactor) {
    this.loadFactor = loadFactor;
    table = new Node[DEFAULT_TABLE_SIZE];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void clear() {
    if (isEmpty()) {
      return;
    }

    size = 0;
    for (Node<K, V> root : table) {
      if (Objects.isNull(root)) {
        continue;
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
    }
  }

  @Override
  public V put(K key, V value) {

    resize();

    int index = index(key);
    Node<K, V> root = table[index];
    if (Objects.isNull(root)) {
      root = createNode(key, value, TypeSupport.returnNull());
      table[index] = root;
      size++;
      postPutProcess(root);
      return null;
    }

    Node<K, V> parent;
    Node<K, V> node = root;
    int cmp;
    int hash = hash(key);
    boolean searched = false;
    Node<K, V> result;
    do {
      parent = node;
      if (hash > node.hash) {
        cmp = 1;
      } else if (hash < node.hash) {
        cmp = -1;
      } else if (Objects.equals(key, node.key)) {
        cmp = 0;
      } else if (Objects.nonNull(key) && Objects.nonNull(node.key)
          && key.getClass() == node.key.getClass() && key instanceof Comparable
          && (cmp = ((Comparable<K>) key).compareTo(node.key)) != 0) {
        // empty
      } else if (searched) {
        cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
      } else {
        if ((Objects.nonNull(node.right) && (result = node(node.right, key).orElseGet(TypeSupport::returnNull)) != null)
            || (Objects.nonNull(node.left) && (result = node(node.left, key).orElseGet(TypeSupport::returnNull)) != null)) {
          node = result;
          cmp = 0;
        } else {
          searched = true;
          cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
        }
      }

      if (cmp > 0) {
        node = node.right;
      } else if (cmp < 0) {
        node = node.left;
      } else {
        node.key = key;
        V oldValue = node.value;
        node.value = value;
        return oldValue;
      }
    } while (Objects.nonNull(node));

    Node<K, V> newNode = createNode(key, value, parent);
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
    for (Node<K, V> root : table) {
      if (Objects.nonNull(root)) {
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
      }
    }
    return false;
  }

  @Override
  public void iterate(BiConsumer<K, V> processor) {
    if (isEmpty()) {
      return;
    }

    Queue<Node<K, V>> queue = new LinkedList<>();
    for (Node<K, V> root : table) {
      if (Objects.nonNull(root)) {
        queue.offer(root);
        while (!queue.isEmpty()) {
          Node<K, V> node = queue.poll();

          processor.accept(node.key, node.value);

          Optional.ofNullable(node.left)
              .ifPresent(queue::offer);

          Optional.ofNullable(node.right)
              .ifPresent(queue::offer);
        }
      }
    }
  }

  protected V remove(Node<K, V> node) {
    V oldValue = node.value;
    Node<K, V> willNode = node;

    // 度为2
    if (node.degreeTwo()) {
      Node<K, V> successor = successor(node);
      Preconditions.checkNotNull(successor);
      node.key = successor.key;
      node.value = successor.value;
      node.hash = successor.hash;
      node = successor;
    }
    Node<K, V> parent = node.parent;

    // 度为2或0
    Node<K, V> child = Optional.ofNullable(node.left)
        .orElse(node.right);
    int index = index(node);
    // 度为1 找到子节点替换
    if (Objects.nonNull(child)) {
      child.parent = parent;
      if (Objects.isNull(parent)) {
        table[index] = child;
      }
      // child在哪边让父节点指向谁
      else if (node.isLeftChild()) {
        parent.left = child;
      } else {
        parent.right = child;
      }

      postRemoveProcess(child);
    }
    // 根节点 度为0
    else if (Objects.isNull(parent)) {
      table[index] = null;

      postRemoveProcess(node);
    }
    // 叶子节点
    else {
      // child在哪边让父节点指向谁
      if (node.isLeftChild()) {
        parent.left = null;
      } else {
        parent.right = null;
      }

      postRemoveProcess(node);
    }

    size--;

    afterRemove(willNode, node);

    return oldValue;
  }

  private void postRemoveProcess(Node<K, V> node) {
    /*
     * 前提: 二叉搜索树度为一采用子节点取代
     * 只需要把replacement染成黑色,让replacement替代node的位置即可
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

    // 删除黑色叶子节点
    Node<K, V> parent = node.parent;
    boolean left = Objects.isNull(parent.left) || node.isLeftChild();
    Node<K, V> sibling = left ? parent.right : parent.left;

    if (left) {
      /*
       * 兄弟是红色
       * node在左
       * 1.sibling染黑 parent染红
       * 2.parent 右旋转
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
       * 兄弟是红色
       * node在右
       * 1.sibling染黑 parent染红
       * 2.parent 右旋转
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
         * 更换兄弟
         *  --B(parent)--> R(sibling) -----
         * |               |               |
         * v               v               v
         * B               B(new sibling)  B(node)
         */
        sibling = parent.left;
      }
      /*
       * 兄弟节点全黑 父节点向下合并
       * sibling染红 parent染黑
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
         * 特殊情况 父节点是黑色
         * 递归处理
         * B(parent) --                    空 --
         * |           |        =>              |
         * v           v                        v
         * B           B(node)      R(sibling)--B(parent)
         */
        if (RBColor.BLACK == parentColor) {
          postRemoveProcess(parent);
        }
      }
      /*
       * B树兄弟节点可以借
       * node在右
       * 1.旋转让sibling变成parent的父节点(LL - LR)
       * 2.让sibling继承parent的颜色
       * 3.把sibling左右染黑
       *    B --> R(parent) -----------
       *          |                    |
       *          v                    v
       * R(可以借)-B(sibling)-R(可以借)   B(node)
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

  private Optional<Node<K, V>> node(K key) {
    final Node<K, V> root = table[index(key)];
    return Objects.isNull(root) ? Optional.empty() : node(root, key);
  }

  private Optional<Node<K, V>> node(Node<K, V> node, K key) {
    int hash = hash(key);
    Node<K, V> result;
    while (Objects.nonNull(node)) {
      if (hash > node.hash) {
        node = node.right;
      } else if (hash < node.hash) {
        node = node.left;
      }

      if (Objects.equals(key, node.key)) {
        return Optional.of(node);
      }

      int cmp;
      if (Objects.nonNull(key) && Objects.nonNull(node.key)
          && key.getClass() == node.key.getClass() && key instanceof Comparable
          && (cmp = ((Comparable<K>) key).compareTo(node.key)) != 0) {
        node = cmp > 0 ? node.right : node.left;
      }

      if (Objects.nonNull(node.right) && (result = node(node.right, key).orElseGet(TypeSupport::returnNull)) != null) {
        return Optional.of(result);
      } else {
        node = node.left;
      }
//      if (Objects.nonNull(node.left)) {
//        Optional<Node<K, V>> optional = node(node.left, key);
//        if (optional.isPresent()) {
//          return optional;
//        }
//      }
    }
    return Optional.empty();
  }

  private int hash(K key) {
    int hash = Objects.hashCode(key);
    return hash ^ (hash >>> 16);
  }

  private int index(K key) {
    return hash(key) & (table.length - 1);
  }

  private int index(Node<K, V> node) {
    return node.hash & (table.length - 1);
  }

  /**
   * 后继节点 <br/> 中序遍历的后继节点
   *
   * @param node 节点
   * @return 后继节点
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

    // node是父节点的左孩子
    while (Objects.nonNull(node.parent) && Objects.equals(node, node.parent.right)) {
      node = node.parent;
    }
    return node.parent;
  }

  private void postPutProcess(Node<K, V> node) {
    Node<K, V> parent = node.parent;
    // 根节点是黑色
    if (Objects.isNull(parent)) {
      Node.black(node);
      return;
    }

    /*
     * 父节点是黑色 可以直接合成B树节点
     *
     * R(sibling)-B(Parent)-node
     */
    if (Node.isBlack(parent)) {
      return;
    }

    // 叔父节点
    Node<K, V> uncle = parent.sibling();
    // 祖父节点 染红
    Node<K, V> grand = Node.red(parent.parent);

    /*
     * 叔父节点是红色
     *
     *                       -- R ---- B ---- R
     *                      |
     *    -- R(Parent) ---- B(Grand) ---- R(Uncle)
     *   |         把祖父节点染红，并当作新添加节点，加入上层B树节点
     * node        父节点和叔父节点染黑 分成两个B树节点
     *
     *          -- R(Grand) --
     *         |              |
     * R(node)-B(Parent)     B(Uncle)
     */
    if (Node.isRed(uncle)) {
      Node.black(parent);
      Node.black(uncle);
      // 把祖父节点当作新添加的节点
      postPutProcess(grand);
      return;
    }

    /*
     * 叔父节点是黑色
     * 要把父节点或自己变成B树中心的黑色节点
     *
     *   R(Parent)-B(Grand)
     *  |
     * node
     *
     * 1.染红G 2.染黑B
     *
     * R(Grand)-B(Parent)-R(node)
     * --------------------------
     *
     *  R(Parent) --B(Grand)
     *         |
     *        node
     *
     * 1.染红G 2.染黑node
     *
     * R(Grand)-B(node)-R(Parent)
     * --------------------------
     *
     *                           -- R ---- B ---- R
     *                          |
     *        -- R(Parent) ---- B(Grand)
     *       |   |
     *     node node LR (1.父节点左旋转 2.祖父节点右旋转)
     * LL (1.祖父右旋转)
     */
    // L 父节点在左
    if (parent.isLeftChild()) {
      // LL node在左
      if (node.isLeftChild()) {
        Node.black(parent);
      }
      // LR node在右
      else {
        Node.black(node);
        rotateLeft(parent);
      }
      rotateRight(grand);
    }
    // R 父节点在右
    else {
      // RR node在右
      if (node.isRightChild()) {
        Node.black(parent);
      }
      // RL node在左
      else {
        Node.black(node);
        rotateRight(parent);
      }
      rotateLeft(grand);
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
    // parent成为子树根节点
    parent.parent = ancestor;
    // grand在左
    if (grand.isLeftChild()) {
      ancestor.left = parent;
    }
    // if grand is right child
    else if (grand.isRightChild()) {
      ancestor.right = parent;
    }
    // grand is root
    else {
      table[index(grand)] = parent;
    }

    grand.parent = parent;
    if (Objects.nonNull(child)) {
      child.parent = grand;
    }

  }

  public void print() {
    if (isEmpty()) {
      return;
    }
    for (int i = 0; i < table.length; i++) {
      final Node<K, V> root = table[i];
      System.out.println("【index = " + i + "】");
      BinaryTrees.println(new BinaryTreeInfo() {
        @Override
        public Object string(Object node) {
          return node;
        }

        @Override
        public Object root() {
          return root;
        }

        @Override
        public Object right(Object node) {
          return ((Node<K, V>) node).right;
        }

        @Override
        public Object left(Object node) {
          return ((Node<K, V>) node).left;
        }
      });
      System.out.println("---------------------------------------------------");
    }
  }

  private void resize() {
    if ((size / table.length) <= loadFactor) {
      return;
    }

    Node<K, V>[] original = table;
    table = new Node[original.length << 1];

    Queue<Node<K, V>> queue = new LinkedList<>();
    for (Node<K, V> root : original) {
      if (Objects.isNull(root)) {
        continue;
      }

      queue.offer(root);

      if (!queue.isEmpty()) {
        Node<K, V> node = queue.poll();

        Optional.ofNullable(node.left)
            .ifPresent(queue::offer);
        Optional.ofNullable(node.right)
            .ifPresent(queue::offer);

        moveNode(node);
      }
    }
  }

  private void moveNode(Node<K, V> newNode) {
    newNode.parent = null;
    newNode.left = null;
    newNode.right = null;
    newNode.color = RBColor.RED;

    int index = index(newNode);
    Node<K, V> root = table[index];
    if (Objects.isNull(root)) {
      root = newNode;
      table[index] = root;
      postPutProcess(root);
      return;
    }

    Node<K, V> parent;
    Node<K, V> node = root;
    int cmp;
    int hash = hash(newNode.key);
    do {
      parent = node;
      if (hash > node.hash) {
        cmp = 1;
      } else if (hash < node.hash) {
        cmp = -1;
      } else if (Objects.nonNull(newNode.key) && Objects.nonNull(node.key)
          && newNode.key.getClass() == node.key.getClass() && newNode.key instanceof Comparable
          && (cmp = ((Comparable<K>) newNode.key).compareTo(node.key)) != 0) {
        // empty
      } else {
        cmp = System.identityHashCode(newNode) - System.identityHashCode(node.key);
      }

      if (cmp > 0) {
        node = node.right;
      } else if (cmp < 0) {
        node = node.left;
      }
    } while (Objects.nonNull(node));

    if (cmp > 0) {
      parent.right = newNode;
    } else {
      parent.left = newNode;
    }

    newNode.parent = parent;

    postPutProcess(newNode);
  }

  protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
    return Node.create(key, value, parent);
  }

  protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
  }

  @Data
  @Accessors(chain = true)
  protected static class Node<K, V> {

    int hash;

    K key;

    V value;

    RBColor color;

    Node<K, V> left;

    Node<K, V> right;

    Node<K, V> parent;

    static <K, V> Node<K, V> create(K key, V value, Node<K, V> parent) {
      int hash = Objects.hashCode(key);
      return new Node<K, V>()
          .setHash(hash ^ (hash >>> 16))
          .setKey(key)
          .setValue(value)
          .setColor(RBColor.RED)
          .setParent(parent);
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

  protected enum RBColor {
    /**
     * 红
     */
    RED,
    /**
     * 黑
     */
    BLACK

  }
}