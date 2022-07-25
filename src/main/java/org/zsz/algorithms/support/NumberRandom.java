package org.zsz.algorithms.support;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Linus Zhang
 * @create 2022-07-23 17:49
 */
public final class NumberRandom {

  private NumberRandom() {
  }

  private static final Random RANDOM = new Random();

  public static int[] randomInts(int size, int bound) {
    int[] result = new int[size];

    for (int i = 0; i < size; i++) {
      result[i] = RANDOM.nextInt(bound);
    }
    return result;
  }

  public static IntStream randomIntStream(int size, int bound) {
    int[] ints = randomInts(size, bound);
    return Arrays.stream(ints);
  }

  public static Integer[] randomIntegers(int size, int bound) {
    Integer[] result = new Integer[size];

    for (int i = 0; i < size; i++) {
      result[i] = RANDOM.nextInt(bound);
    }
    return result;
  }

}
