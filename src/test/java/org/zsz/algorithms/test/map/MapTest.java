package org.zsz.algorithms.test.map;

import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;
import org.zsz.algorithms.map.HashMap;
import org.zsz.algorithms.map.Map;
import org.zsz.algorithms.map.TreeMap;
import org.zsz.algorithms.support.TypeSupport;
import org.zsz.algorithms.test.entity.Key;
import org.zsz.algorithms.test.entity.Person;
import org.zsz.algorithms.test.entity.SubKey1;
import org.zsz.algorithms.test.entity.SubKey2;

/**
 * @author Linus Zhang
 * @create 2022-07-07 22:36
 */
public class MapTest {

  @Test
  public void testTreeMapPut() {
    Map<String, Integer> map = new TreeMap<>();
    map.put("class", 2);
    map.put("public", 5);
    map.put("public", 8);
    map.put("text", 6);
    Assert.assertEquals(3, map.size());
    map.iterate((k, v) -> System.out.printf("%s: %s%n", k, v));
  }

  @Test
  public void testTreeMapRemove() {
    Map<String, Integer> map = new TreeMap<>();
    map.put("class", 2);
    map.put("public", 5);
    map.put("public", 8);
    map.put("text", 6);
    map.remove("public");
    Assert.assertEquals(2, map.size());
    map.iterate((k, v) -> System.out.printf("%s: %s%n", k, v));
  }

  @Test
  public void testHashMapPut() {
    Person p1 = new Person(100, "linus");
    Person p2 = new Person(100, "linus");
    Person p3 = new Person(1, "aaa");
    Person p4 = new Person(2, "bbb");
    Person p5 = new Person(2, "bbb");
    Map<Person, String> map = new HashMap<>();
    map.put(p1, "111");
    map.put(p2, "222");
    map.put(p3, "333");
    map.put(p4, "444");
    map.put(p5, "555");
    Assert.assertEquals(3, map.size());
    map.iterate((k, v) -> System.out.printf("%s: %s%n", k, v));
  }

  @Test
  public void testHashMapGet() {
    Person p1 = new Person(100, "linus");
    Person p2 = new Person(100, "linus");
    Person p3 = new Person(1, "aaa");
    Person p4 = new Person(2, "bbb");
    Person p5 = new Person(2, "bbb");
    Map<Person, String> map = new HashMap<>();
    map.put(p1, "111");
    map.put(p2, "222");
    map.put(p3, "333");
    map.put(p4, "444");
    map.put(p5, "555");
    map.put(null, "666");
    Assert.assertEquals("555", map.get(p4));
    Assert.assertEquals("666", map.get(null));
    map.iterate((k, v) -> System.out.printf("%s: %s%n", k, v));
  }

  @Test
  public void testHashMapRemove() {
    Person p1 = new Person(100, "linus");
    Person p2 = new Person(100, "linus");
    Person p3 = new Person(1, "aaa");
    Person p4 = new Person(2, "bbb");
    Person p5 = new Person(2, "bbb");
    Map<Person, String> map = new HashMap<>();
    map.put(p1, "111");
    map.put(p2, "222");
    map.put(p3, "333");
    map.put(p4, "444");
    map.put(p5, "555");
    map.remove(p1);

    Assert.assertEquals(2, map.size());
    map.iterate((k, v) -> System.out.printf("%s: %s%n", k, v));
  }

  @Test
  public void testHashMap_0() {
    Map<Object, Integer> map = new HashMap<>();
    for (int i = 1; i <= 20; i++) {
      map.put(new Key(i), i);
    }
    for (int i = 5; i <= 7; i++) {
      map.put(new Key(i), i + 5);
    }
    Assert.assertEquals(20, map.size());

    Assert.assertEquals(4, (int) map.get(new Key(4)));
    Assert.assertEquals(10, (int) map.get(new Key(5)));
    Assert.assertEquals(11, (int) map.get(new Key(6)));
    Assert.assertEquals(12, (int) map.get(new Key(7)));
    Assert.assertEquals(8, (int) map.get(new Key(8)));
    HashMap<Object, Integer> hashMap = TypeSupport.unsafeCast(map);
    hashMap.print();
  }

  @Test
  public void testHashMap_1() {

    Map<Object, Integer> map = new HashMap<>();
    for (int i = 1; i <= 20; i++) {
      map.put(new SubKey1(i), i);
    }
    map.put(new SubKey2(1), 5);
    Assert.assertEquals(5, (int) map.get(new SubKey1(1)));
    Assert.assertEquals(5, (int) map.get(new SubKey2(1)));
    Assert.assertEquals(20, map.size());
    HashMap<Object, Integer> hashMap = TypeSupport.unsafeCast(map);
    hashMap.print();
  }

  @Test
  public void testHashMap_2() {
    Map<Integer, Integer> map = new HashMap<>();

    IntStream.range(0, 10000)
        .forEach(i -> map.put(i, i));

    Assert.assertEquals(1, (int) map.get(1));
    Assert.assertEquals(5000, (int) map.get(5000));
    Assert.assertEquals(9999, (int) map.get(9999));
  }

}
