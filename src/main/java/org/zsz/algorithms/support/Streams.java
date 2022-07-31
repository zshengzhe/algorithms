package org.zsz.algorithms.support;

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * @author Linus Zhang
 * @create 2022-07-31 12:36
 */
public final class Streams {

  private Streams() {
  }

  public static IntStream revRange(int from, int to) {
    return IntStream.range(from, to)
        .map(i -> to - i + from - 1);
  }

  /**
   * IntStream无法从小到大 该方法可以解决该问题
   * <p>
   * 小数字闭区间 大数字开区间
   *
   * @param from 开始
   * @param to   结束
   * @return IntStream
   */
  public static IntStream range(int from, int to) {
    final boolean reverse = from > to;
    IntUnaryOperator operator = i -> reverse ? to - i + from - 1 : i;
    return IntStream.range(reverse ? to : from, reverse ? from : to)
        .map(operator);
  }

  /**
   * IntStream无法从小到大 该方法可以解决该问题
   * <p>
   * 闭区间
   *
   * @param from 开始
   * @param to   结束
   * @return IntStream
   */
  public static IntStream rangeClosed(int from, int to) {
    final boolean reverse = from > to;
    IntUnaryOperator operator = i -> reverse ? to - i + from : i;
    return IntStream.rangeClosed(reverse ? to : from, reverse ? from : to)
        .map(operator);
  }

}
