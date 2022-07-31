package org.zsz.algorithms.support;

import com.google.common.base.Preconditions;
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

  public static int[] randomInts(int size, int min, int max) {
    Preconditions.checkArgument(size > 0);
    Preconditions.checkArgument(max > min);

    int[] array = new int[size];
    int delta = max - min + 1;
    for (int i = 0; i < size; i++) {
      array[i] = min + (int) (Math.random() * delta);
    }
    return array;
  }

  public static Integer[] randomIntegers(int size, int min, int max) {
    Preconditions.checkArgument(size > 0);
    Preconditions.checkArgument(max > min);

    Integer[] array = new Integer[size];
    int delta = max - min + 1;
    for (int i = 0; i < size; i++) {
      array[i] = min + (int) (Math.random() * delta);
    }
    return array;
  }

  public static int[] tailAscOrder(int min, int max, int disorderCount) {
    int[] array = Streams.rangeClosed(min, max)
        .boxed()
        .mapToInt(i -> i)
        .toArray();

    if (disorderCount > array.length) {
      return array;
    }

    reverseInts(array, 0, disorderCount);
    return array;
  }

  private static void reverseInts(int[] array, int begin, int end) {
    int count = (end - begin) >> 1;
    int sum = begin + end - 1;
    for (int i = begin; i < begin + count; i++) {
      int j = sum - i;
      int tmp = array[i];
      array[i] = array[j];
      array[j] = tmp;
    }
  }

}
