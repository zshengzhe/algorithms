package org.zsz.algorithms.tree;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.zsz.algorithms.support.TypeSupport;

/**
 * 红黑树
 *
 * @author Linus Zhang
 * @create 2022-06-08 21:46
 */
public class RedBlackTree<E> extends BalancedBinarySearchTree<E> {

  public RedBlackTree() {
    this(TypeSupport.returnNull());
  }

  public RedBlackTree(Comparator<E> comparator) {
    super(comparator);
  }

  @Override
  protected void postAddProcess(Node<E> node) {
    Node<E> parent = node.parent;
    // 根节点是黑色
    if (Objects.isNull(parent)) {
      RBNode.black(node);
      return;
    }

    /*
     * 父节点是黑色 可以直接合成B树节点
     *
     * R(sibling)-B(Parent)-node
     */
    if (RBNode.isBlack(parent)) {
      return;
    }

    // 叔父节点
    Node<E> uncle = parent.sibling();
    // 祖父节点 染红
    Node<E> grand = RBNode.red(parent.parent);

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
    if (RBNode.isRed(uncle)) {
      RBNode.black(parent);
      RBNode.black(uncle);
      // 把祖父节点当作新添加的节点
      postAddProcess(grand);
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
        RBNode.black(parent);
      }
      // LR node在右
      else {
        RBNode.black(node);
        rotateLeft(parent);
      }
      rotateRight(grand);
    }
    // R 父节点在右
    else {
      // RR node在右
      if (node.isRightChild()) {
        RBNode.black(parent);
      }
      // RL node在左
      else {
        RBNode.black(node);
        rotateRight(parent);
      }
      rotateLeft(grand);
    }
  }

  @Override
  protected void postRemoveProcess(Node<E> node) {
    /*
     * 前提: 二叉搜索树度为一采用子节点取代
     * 只需要把replacement染成黑色,让replacement替代node的位置即可
     *  -- R ---- B
     * |
     * B(node) -- R(replacement)
     */
    if (RBNode.isRed(node)) {
      RBNode.black(node);
      return;
    }

    // root
    if (node.isRoot()) {
      return;
    }

    // 删除黑色叶子节点
    Node<E> parent = node.parent;
    boolean left = Objects.isNull(parent.left) || node.isLeftChild();
    Node<E> sibling = left ? parent.right : parent.left;

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
      if (RBNode.isRed(sibling)) {
        RBNode.black(sibling);
        RBNode.red(parent);
        rotateLeft(parent);
        sibling = parent.right;
      }
      if (RBNode.isBlack(sibling.left) && RBNode.isBlack(sibling.right)) {
        RBColor parentColor = RBNode.colorOf(parent);
        RBNode.black(parent);
        RBNode.red(sibling);
        if (RBColor.BLACK == parentColor) {
          postRemoveProcess(parent);
        }
      } else {
        if (RBNode.isBlack(sibling.right)) {
          rotateRight(sibling);
          sibling = parent.right;
        }
        RBNode.dyeing(sibling, RBNode.colorOf(parent));
        RBNode.black(sibling.right);
        RBNode.black(parent);
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
      if (RBNode.isRed(sibling)) {
        RBNode.black(sibling);
        RBNode.red(parent);
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
      if (RBNode.isBlack(sibling.left) && RBNode.isBlack(sibling.right)) {
        RBColor parentColor = RBNode.colorOf(parent);
        RBNode.black(parent);
        RBNode.red(sibling);
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
        if (RBNode.isBlack(sibling.left)) {
          rotateLeft(sibling);
          sibling = parent.left;
        }
        RBNode.dyeing(sibling, RBNode.colorOf(parent));
        RBNode.black(sibling.left);
        RBNode.black(parent);
        rotateRight(parent);

      }
    }
  }

//  private void postRemoveProcess2(Node<E> node, Node<E> replacement) {
//    // B树只会在B树叶子节点中删除，红节点不影响B树节点中的核心黑节点
//    if (RBNode.isRed(node)) {
//      return;
//    }
//
//    /*
//     * 前提: 二叉搜索树度为一采用子节点取代
//     * 只需要把replacement染成黑色,让replacement替代node的位置即可
//     *  -- R ---- B
//     * |
//     * B(node) -- R(replacement)
//     */
//    if (RBNode.isRed(replacement)) {
//      RBNode.black(replacement);
//      return;
//    }
//
//    // root
//    if (node.isRoot()) {
//      return;
//    }
//
//    // 删除黑色叶子节点
//    Node<E> parent = node.parent;
//    boolean left = Objects.isNull(parent.left) || node.isLeftChild();
//    Node<E> sibling = left ? parent.right : parent.left;
//
//    if (left) {
//      /*
//       * 兄弟是红色
//       * node在左
//       * 1.sibling染黑 parent染红
//       * 2.parent 右旋转
//       *
//       * B(parent) --> R(sibling) --
//       * |             |            |
//       * v             v            v
//       * B(node)       B            B
//       * ------------------------------------
//       *  ------------ R(parent) <-- B(sibling) --
//       * |             |                          |
//       * v             v                          v
//       * B(node)       B                          B
//       */
//      if (RBNode.isRed(sibling)) {
//        RBNode.black(sibling);
//        RBNode.red(parent);
//        rotateLeft(parent);
//        sibling = parent.right;
//      }
//      if (RBNode.isBlack(sibling.left) && RBNode.isBlack(sibling.right)) {
//        RBColor parentColor = RBNode.colorOf(parent);
//        RBNode.black(parent);
//        RBNode.red(sibling);
//        if (RBColor.BLACK == parentColor) {
//          postRemoveProcess2(parent, null);
//        }
//      } else {
//        if (RBNode.isBlack(sibling.right)) {
//          rotateRight(sibling);
//          sibling = parent.right;
//        }
//        RBNode.dyeing(sibling, RBNode.colorOf(parent));
//        RBNode.black(sibling.right);
//        RBNode.black(parent);
//        rotateLeft(parent);
//
//      }
//    } else {
//      /*
//       * 兄弟是红色
//       * node在右
//       * 1.sibling染黑 parent染红
//       * 2.parent 右旋转
//       *
//       *  -- R(sibling) <-- B(parent) --
//       * |       |                      |
//       * v       v                      v
//       * B       B                      B(node)
//       * ------------------------------------
//       *  -- B(sibling) --> R(parent) ---
//       * |                  |            |
//       * v                  v            v
//       * B                  B            B(node)
//       */
//      if (RBNode.isRed(sibling)) {
//        RBNode.black(sibling);
//        RBNode.red(parent);
//        rotateRight(parent);
//        /*
//         * 更换兄弟
//         *  --B(parent)--> R(sibling) -----
//         * |               |               |
//         * v               v               v
//         * B               B(new sibling)  B(node)
//         */
//        sibling = parent.left;
//      }
//      /*
//       * 兄弟节点全黑 父节点向下合并
//       * sibling染红 parent染黑
//       * B --> R(parent) ---
//       *       |            |
//       *       v            v
//       *       B(sibling)   B(node)
//       * ----------------------------------------
//       * B --> B(parent)      B ----------------
//       * |     |          =>  |                 |
//       * v     v              v                 v
//       * B     R(sibling)     B     R(sibling)--B(parent)
//       */
//      if (RBNode.isBlack(sibling.left) && RBNode.isBlack(sibling.right)) {
//        RBColor parentColor = RBNode.colorOf(parent);
//        RBNode.black(parent);
//        RBNode.red(sibling);
//        /*
//         * 特殊情况 父节点是黑色
//         * 递归处理
//         * B(parent) --                    空 --
//         * |           |        =>              |
//         * v           v                        v
//         * B           B(node)      R(sibling)--B(parent)
//         */
//        if (RBColor.BLACK == parentColor) {
//          postRemoveProcess2(parent, null);
//        }
//      }
//      /*
//       * B树兄弟节点可以借
//       * node在右
//       * 1.旋转让sibling变成parent的父节点(LL - LR)
//       * 2.让sibling继承parent的颜色
//       * 3.把sibling左右染黑
//       *    B --> R(parent) -----------
//       *          |                    |
//       *          v                    v
//       * R(可以借)-B(sibling)-R(可以借)   B(node)
//       * --------------------------------------
//       *    B --> R(sibling) --
//       *          |            |
//       *          v            v
//       *          B            B(parent)
//       */
//      else {
//        if (RBNode.isBlack(sibling.left)) {
//          rotateLeft(sibling);
//          sibling = parent.left;
//        }
//        RBNode.dyeing(sibling, RBNode.colorOf(parent));
//        RBNode.black(sibling.left);
//        RBNode.black(parent);
//        rotateRight(parent);
//
//      }
//    }
//  }

  @Override
  protected Node<E> createNode(E element) {
    return RBNode.create(element);
  }

  @Override
  protected Node<E> createNode(E element, Node<E> parent) {
    return RBNode.create(element, parent);
  }

  @Data
  @Accessors(chain = true)
  @EqualsAndHashCode(callSuper = true)
  private static class RBNode<E> extends Node<E> {

    RBColor color;

    static <E> RBNode<E> create(E element) {
      return new RBNode<E>()
          .setElement(element)
          .setColor(RBColor.RED);
    }

    static <E> RBNode<E> create(E element, Node<E> parent) {
      return new RBNode<E>()
          .setElement(element)
          .setColor(RBColor.RED)
          .setParent(parent);
    }

    static <E> RBNode<E> cast(Node<E> node) {
      return TypeSupport.unsafeCast(node);
    }

    static <E> RBNode<E> red(Node<E> node) {
      return dyeing(node, RBColor.RED);
    }

    static <E> RBNode<E> black(Node<E> node) {
      return dyeing(node, RBColor.BLACK);
    }

    static <E> RBNode<E> dyeing(Node<E> node, RBColor color) {
      if (Objects.isNull(node)) {
        return TypeSupport.returnNull();
      }

      RBNode<E> rbNode = RBNode.cast(node);
      rbNode.color = color;

      return rbNode;
    }

    static <E> RBColor colorOf(Node<E> node) {
      return Optional.ofNullable(node)
          .map(RBNode::cast)
          .map(RBNode::getColor)
          .orElse(RBColor.BLACK);
    }

    static <E> boolean isBlack(Node<E> node) {
      return colorOf(node) == RBColor.BLACK;
    }

    static <E> boolean isRed(Node<E> node) {
      return colorOf(node) == RBColor.RED;
    }

    @Override
    public RBNode<E> setElement(E element) {
      super.setElement(element);
      return this;
    }

    @Override
    public RBNode<E> setLeft(Node<E> left) {
      super.setLeft(left);
      return this;
    }

    @Override
    public RBNode<E> setRight(Node<E> right) {
      super.setRight(right);
      return this;
    }

    @Override
    public RBNode<E> setParent(Node<E> parent) {
      super.setParent(parent);
      return this;
    }

    @Override
    public String toString() {
      if (isRed(this)) {
        return "R_" + element.toString();
      } else {
        return "B_" + element.toString();
      }
    }
  }

  private enum RBColor {
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
