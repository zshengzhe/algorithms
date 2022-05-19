package org.zsz.algorithms.support;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;

/**
 * TODO
 * 二叉树打印器
 * <p>
 * 需要二叉树中存在root表示根节点
 * <p>
 * 需要节点中存在element、left和right字段，以表示元素、左节点和右节点
 *
 * @author Linus Zhang
 * @create 2022-05-10 22:26
 */
public final class BinaryTreePrinter {

  private static final String ROOT_FIELD_NAME = "root";

  private static final String CREATE_PRINTER_ERROR = "Create BinaryTreePrinter Error";

  private final Class<?> nodeType;

  private final Field elementField;

  private final Field leftField;

  private final Field rightField;

  private BinaryTreePrinter(Class<?> nodeType) throws NoSuchFieldException {
    this.nodeType = nodeType;
    this.elementField = nodeType.getDeclaredField("element");
    this.elementField.setAccessible(true);

    this.leftField = nodeType.getDeclaredField("left");
    this.leftField.setAccessible(true);

    this.rightField = nodeType.getDeclaredField("right");
    this.rightField.setAccessible(true);
  }

  private static BinaryTreePrinter create(Object tree) {
    Class<?> treeClass = tree.getClass();
    try {
      Field rootField = treeClass.getDeclaredField(ROOT_FIELD_NAME);
      Preconditions.checkNotNull(rootField);

      return new BinaryTreePrinter(rootField.getType());

    } catch (Exception e) {
      throw new RuntimeException(CREATE_PRINTER_ERROR, e);
    }
  }

  public static void println(Object tree) {
    print(tree);
    System.out.println();
  }

  public static void print(Object tree) {
    create(tree).doPrint();
    System.out.println();
  }

  public void doPrint() {
    System.out.println(this.nodeType);
    System.out.println(this.elementField);
    System.out.println(this.leftField);
    System.out.println(this.rightField);
  }

}
