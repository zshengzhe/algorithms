package org.zsz.algorithms.test.sort;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zsz.algorithms.sort.BubbleSort;
import org.zsz.algorithms.support.NumberRandom;
import org.zsz.algorithms.support.Streams;
import org.zsz.algorithms.support.Times;

/**
 * @author Linus Zhang
 * @create 2022-07-31 11:31
 */
@Slf4j
public class BubbleSortTest {

  @Test
  public void testSortWithRandom() {
    final int[] array1 = NumberRandom.randomInts(10000, 1, 100000);
    final int[] array2 = Arrays.copyOf(array1, array1.length);
    final int[] array3 = Arrays.copyOf(array1, array1.length);

    Times.timing("BubbleSort1", () -> {
      BubbleSort.sort1(array1);
      return null;
    });
    log.info("array1 -> {}", Arrays.toString(array1));

    Times.timing("BubbleSort2", () -> {
      BubbleSort.sort2(array2);
      return null;
    });
    log.info("array2 -> {}", Arrays.toString(array2));

    Times.timing("BubbleSort3", () -> {
      BubbleSort.sort3(array3);
      return null;
    });
    log.info("array3 -> {}", Arrays.toString(array3));
  }

  @Test
  public void testSortWithAsc() {
    final int[] array1 = Streams.rangeClosed(1, 10000)
        .boxed()
        .mapToInt(i -> i)
        .toArray();

    final int[] array2 = Arrays.copyOf(array1, array1.length);
    final int[] array3 = Arrays.copyOf(array1, array1.length);

    Times.timing("BubbleSort1", () -> {
      BubbleSort.sort1(array1);
      return null;
    });
    log.info("array1 -> {}", Arrays.toString(array1));

    Times.timing("BubbleSort2", () -> {
      BubbleSort.sort2(array2);
      return null;
    });
    log.info("array2 -> {}", Arrays.toString(array2));

    Times.timing("BubbleSort3", () -> {
      BubbleSort.sort3(array3);
      return null;
    });
    log.info("array3 -> {}", Arrays.toString(array3));
  }

  @Test
  public void testSortWithTailAsc() {
    final int[] array1 = NumberRandom.tailAscOrder(1, 10000, 2000);
    final int[] array2 = Arrays.copyOf(array1, array1.length);
    final int[] array3 = Arrays.copyOf(array1, array1.length);

    Times.timing("BubbleSort1", () -> {
      BubbleSort.sort1(array1);
      return null;
    });
    log.info("array1 -> {}", Arrays.toString(array1));

    Times.timing("BubbleSort2", () -> {
      BubbleSort.sort2(array2);
      return null;
    });
    log.info("array2 -> {}", Arrays.toString(array2));

    Times.timing("BubbleSort3", () -> {
      BubbleSort.sort3(array3);
      return null;
    });
    log.info("array3 -> {}", Arrays.toString(array3));
  }

}
