package org.zsz.algorithms.sort;

/**
 * @author Linus Zhang
 * @create 2022-07-30 00:31
 */
public final class BubbleSort {

  private BubbleSort() {
  }

  public static void sort1(int[] array) {
    for (int end = array.length - 1; end > 0; end--) {
      for (int begin = 1; begin <= end; begin++) {
        if (array[begin] < array[begin - 1]) {
          int tmp = array[begin];
          array[begin] = array[begin - 1];
          array[begin - 1] = tmp;
        }
      }
    }
  }

  public static void sort2(int[] array) {
    for (int end = array.length - 1; end > 0; end--) {
      boolean sorted = true;
      for (int begin = 1; begin <= end; begin++) {
        if (array[begin] < array[begin - 1]) {
          int tmp = array[begin];
          array[begin] = array[begin - 1];
          array[begin - 1] = tmp;
          sorted = false;
        }
      }
      if (sorted) {
        break;
      }
    }
  }

  public static void sort3(int[] array) {
    for (int end = array.length - 1; end > 0; end--) {
      // 初始值在数据完全有序时生效
      int sortedIndex = 1;
      for (int begin = 1; begin <= end; begin++) {
        if (array[begin] < array[begin - 1]) {
          int tmp = array[begin];
          array[begin] = array[begin - 1];
          array[begin - 1] = tmp;
          sortedIndex = begin;
        }
      }
      end = sortedIndex;
    }
  }


}
