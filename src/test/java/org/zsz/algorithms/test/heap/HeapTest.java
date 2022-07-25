package org.zsz.algorithms.test.heap;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.zsz.algorithms.heap.BinaryHeap;
import org.zsz.algorithms.support.NumberRandom;
import org.zsz.algorithms.support.printer.BinaryTrees;

/**
 * @author Linus Zhang
 * @create 2022-07-21 22:12
 */
public class HeapTest {

  @Test
  public void testBinaryHeapAdd() {
    BinaryHeap<Integer> heap = new BinaryHeap<>();
    heap.offer(68)
        .offer(72)
        .offer(43)
        .offer(50)
        .offer(38);
    Assert.assertEquals(72, (int) heap.poll());
    BinaryTrees.println(heap);
  }

  @Test
  public void testBinaryHeapRemove() {
    BinaryHeap<Integer> heap = new BinaryHeap<>();
    heap.offer(68)
        .offer(72)
        .offer(43)
        .offer(50)
        .offer(38)
        .offer(10)
        .offer(90)
        .offer(65);
    Assert.assertEquals(90, (int) heap.poll());
    Assert.assertEquals(72, (int) heap.poll());
    BinaryTrees.println(heap);
  }

  @Test
  public void testHeapify() {
    BinaryHeap<Integer> heap = new BinaryHeap<>();
    Integer[] ints = NumberRandom.randomIntegers(10, 100);
    Arrays.stream(ints)
        .forEach(heap::offer);
    BinaryTrees.println(heap);

    BinaryHeap<Integer> heap1 = BinaryHeap.of(ints);
    BinaryTrees.println(heap1);
    Assert.assertEquals(heap.poll(), heap1.poll());
  }

}
