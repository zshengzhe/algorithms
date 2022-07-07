package org.zsz.algorithms.test.map;

import org.junit.Assert;
import org.junit.Test;
import org.zsz.algorithms.map.Map;
import org.zsz.algorithms.map.TreeMap;

/**
 * @author Linus Zhang
 * @create 2022-07-07 22:36
 */
public class TreeMapTest {

  @Test
  public void testPut() {
    Map<String, Integer> map = new TreeMap<>();
    map.put("class", 2);
    map.put("public", 5);
    map.put("public", 8);
    map.put("text", 6);
    Assert.assertEquals(3, map.size());
    map.iterate((k, v) -> System.out.printf("%s: %s%n", k, v));
  }

  @Test
  public void testRemove() {
    Map<String, Integer> map = new TreeMap<>();
    map.put("class", 2);
    map.put("public", 5);
    map.put("public", 8);
    map.put("text", 6);
    map.remove("public");
    Assert.assertEquals(2, map.size());
    map.iterate((k, v) -> System.out.printf("%s: %s%n", k, v));
  }

}
