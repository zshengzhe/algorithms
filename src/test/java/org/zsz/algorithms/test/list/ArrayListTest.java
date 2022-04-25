package org.zsz.algorithms.test.list;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zsz.algorithms.list.ArrayList;
import org.zsz.algorithms.list.List;

/**
 * @author Zhang Shengzhe
 * @create 2020-12-25 16:16
 */
@Slf4j
public class ArrayListTest {

  @Test
  public void testAdd() {
    List<Integer> list = new ArrayList<>();

    list.add(77);
    list.add(88);
    list.add(99);
    list.add(11);
    list.add(22);
    list.add(33);
    list.add(44);
    list.add(55);
    list.add(66);
    list.add(777);
    list.add(888);
    list.add(999);

    log.info("ArrayList -> {}", list);
  }

}