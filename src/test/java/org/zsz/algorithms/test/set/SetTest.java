package org.zsz.algorithms.test.set;

import org.junit.Assert;
import org.junit.Test;
import org.zsz.algorithms.set.LinkedListSet;
import org.zsz.algorithms.set.RedBlackTreeSet;
import org.zsz.algorithms.set.Set;

/**
 * @author Linus Zhang
 * @create 2022-07-04 23:52
 */
public class SetTest {

  @Test
  public void testLinkedListSetAddWithInteger() {
    Set<Integer> set = new LinkedListSet<>();
    set.add(10)
        .add(11)
        .add(11)
        .add(12)
        .add(10);
    Assert.assertEquals(3L, set.size());
    set.iterate(System.out::println);
  }

  @Test
  public void testLinkedListSetRemoveWithInteger() {
    Set<Integer> set = new LinkedListSet<>();
    set.add(10)
        .add(11)
        .add(11)
        .add(12)
        .add(10);
    Assert.assertEquals(3L, set.size());
    set.iterate(System.out::println);
  }

  @Test
  public void testRedBlackTreeSetAddWithInteger() {
    Set<Integer> set = new RedBlackTreeSet<>();
    set.add(10)
        .add(11)
        .add(12)
        .add(12)
        .add(13)
        .remove(10);
    Assert.assertEquals(3L, set.size());
    set.iterate(System.out::println);
  }

  @Test
  public void testRedBlackTreeSetRemoveWithInteger() {
    Set<Integer> set = new RedBlackTreeSet<>();
    set.add(10)
        .add(11)
        .add(12)
        .add(12)
        .add(13)
        .remove(10);
    Assert.assertEquals(3L, set.size());
    set.iterate(System.out::println);
  }

}
