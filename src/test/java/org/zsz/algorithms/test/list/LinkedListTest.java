package org.zsz.algorithms.test.list;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.zsz.algorithms.list.CircleLinkedList;
import org.zsz.algorithms.list.CircleLinkedListV2;
import org.zsz.algorithms.list.LinkedList;
import org.zsz.algorithms.list.List;
import org.zsz.algorithms.list.SingleCircleLinkedList;
import org.zsz.algorithms.list.SingleLinkedList;
import org.zsz.algorithms.list.SingleLinkedListV2;

/**
 * @author Zhang Shengzhe
 * @create 2021-03-23 23:47
 */
@Slf4j
public class LinkedListTest {

  private void _assert(List<Integer> list) {
    list.add(11);
    list.add(22);
    list.add(33);
    list.add(44);

    list.add(0, 55); // [55, 11, 22, 33, 44]
    list.add(2, 66); // [55, 11, 66, 22, 33, 44]
    list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]

    list.remove(0); // [11, 66, 22, 33, 44, 77]
    list.remove(2); // [11, 66, 33, 44, 77]
    list.remove(list.size() - 1); // [11, 66, 33, 44]

    Assert.assertEquals(3, list.indexOf(44));
    Assert.assertEquals(List.ELEMENT_NOT_FOUND, list.indexOf(22));
    Assert.assertTrue(list.contains(33));
    Assert.assertEquals(11, (int) list.get(0));
    Assert.assertEquals(66, (int) list.get(1));
    Assert.assertEquals(44, (int) list.get(list.size() - 1));
  }

  @Test
  public void testSingleLinkedList() {
    List<Integer> list = new SingleLinkedList<>();
    _assert(list);
    log.info("SingleLinkedList -> {}", list);
  }

  @Test
  public void testSingleLinkedListV2() {
    List<Integer> list = new SingleLinkedListV2<>();
    _assert(list);
    log.info("SingleLinkedListV2 -> {}", list);
  }

  @Test
  public void testLinkedList() {
    List<Integer> list = new LinkedList<>();
    _assert(list);
    log.info("LinkedList -> {}", list);
  }

  @Test
  public void singleCircleLinkedList() {
    List<Integer> list = new SingleCircleLinkedList<>();
    _assert(list);
    log.info("SingleCircleLinkedList -> {}", list);
  }

  @Test
  public void circleLinkedList() {
    List<Integer> list = new CircleLinkedList<>();
    _assert(list);
    log.info("CircleLinkedList -> {}", list);
  }

  @Test
  public void josephusProblem() {
    CircleLinkedListV2<Integer> list = new CircleLinkedListV2<>();
    for (int i = 1; i <= 8; i++) {
      list.add(i);
    }
    list.reset();
    java.util.List<Integer> result = new java.util.ArrayList<>(list.size());
    while (!list.isEmpty()) {
      for (int i = 0; i < 2; i++) {
        list.next();
      }
      result.add(list.remove());
    }
    result.forEach(System.out::println);
  }

}
