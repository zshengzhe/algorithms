package org.zsz.algorithms.support;

import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 函数工具类
 *
 * @author Linus Zhang
 * @create 2022-04-28 00:03
 */
public final class Functions {

  private Functions() {
  }

  public static <T> IntConsumer noInputIntConsumer(Supplier<T> supplier) {
    return i -> supplier.get();
  }

}
