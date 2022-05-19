package org.zsz.algorithms.test.queue;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.zsz.algorithms.queue.CircleDeque;
import org.zsz.algorithms.queue.CircleQueue;
import org.zsz.algorithms.queue.Deque;
import org.zsz.algorithms.queue.DequeLinkedImpl;
import org.zsz.algorithms.queue.Queue;
import org.zsz.algorithms.queue.QueueLinkedImpl;
import org.zsz.algorithms.support.Functions;

/**
 * @author Zhang Shengzhe
 * @create 2021-03-31 22:27
 */
@Slf4j
public class QueueTest {

  private final static int[] ARRAY = {11, 22, 33, 44};

  @Test
  public void testQueue() {
    final Queue<Integer> queue = new QueueLinkedImpl<>();

    Arrays.stream(ARRAY).forEach(queue::offer);

    int index = 0;
    while (!queue.isEmpty()) {
      Integer poll = queue.poll();
      Assert.assertSame(ARRAY[index++], poll);
      log.info("{}", poll);
    }

  }

  @Test
  public void testDeque() {
    final Deque<Integer> queue = new DequeLinkedImpl<>();

    Arrays.stream(ARRAY).forEach(queue::offerFirst);

    int index = 3;
    while (!queue.isEmpty()) {
      Integer poll = queue.pollFirst();
      Assert.assertSame(ARRAY[index--], poll);
      log.info("{}", poll);
    }
  }

  @Test
  public void testCircleQueue() {
    final Queue<Integer> queue = new CircleQueue<>();
    IntStream.range(0, 10).forEach(queue::offer);

    IntConsumer poller = Functions.noInputIntConsumer(queue::poll);
    IntStream.range(0, 5).forEach(poller);

    IntStream.range(10, 20)
        .forEach(queue::offer);

    int num = 5;
    while (!queue.isEmpty()) {
      Integer poll = queue.poll();
      Assert.assertSame(poll, num++);
      log.info("{}", poll);
    }
  }

  @Test
  public void testCircleDeque() {
    final Deque<Integer> deque = new CircleDeque<>();
    IntConsumer adder = i -> {
      deque.offerFirst(i + 1);
      deque.offerLast(i + 101);
    };
    IntStream.range(0, 10).forEach(adder);

    IntConsumer preAssert = i -> {
      Integer first = deque.pollFirst();
      Assert.assertSame(first, i);
      log.info("{}", first);
    };
    Functions.rangeClosed(10, 1)
        .forEach(preAssert);

    IntConsumer postAssert = i -> {
      Integer first = deque.pollLast();
      Assert.assertSame(first, i);
      log.info("{}", first);
    };
    Functions.rangeClosed(110, 101)
        .forEach(postAssert);
  }

}