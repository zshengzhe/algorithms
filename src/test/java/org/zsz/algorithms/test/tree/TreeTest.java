package org.zsz.algorithms.test.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntConsumer;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import org.zsz.algorithms.support.printer.BinaryTrees;
import org.zsz.algorithms.test.entity.Person;
import org.zsz.algorithms.tree.AvlTree;
import org.zsz.algorithms.tree.BinarySearchTree;
import org.zsz.algorithms.tree.RedBlackTree;
import org.zsz.algorithms.tree.Trie;
import org.zsz.algorithms.tree.TrieImpl;

/**
 * @author Zhang Shengzhe
 * @create 2021-04-05 14:01
 */
public class TreeTest {

  private static final Integer[] INTS = new Integer[]{7, 4, 9, 2, 5, 8, 11, 3, 12, 1};

  private static final Stream<Integer> INT_STREAM = Arrays.stream(INTS);

  @Test
  public void addBinarySearchTreeWithInteger() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    INT_STREAM.forEach(tree::add);
    Assert.assertTrue(tree.contains(7));
    BinaryTrees.println(tree);
    System.out.println(tree.height());
  }

  @Test
  public void addBinarySearchTreeWithEntity() {
    BinarySearchTree<Person> tree = new BinarySearchTree<>(Comparator.comparingInt(Person::getAge));
    tree.add(new Person(20, "aaa"));
    tree.add(new Person(10, "bbb"));
    tree.add(new Person(30, "ccc"));
    Assert.assertTrue(tree.contains(new Person(20, "aaa")));
    BinaryTrees.println(tree);
  }

  @Test
  public void preorder() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    INT_STREAM.forEach(tree::add);

    BinaryTrees.println(tree);

    StringBuilder builder = new StringBuilder();
    tree.preorder(e -> builder.append(e).append(", "));
    String result = builder.delete(builder.length() - 2, builder.length()).toString();

    System.out.println(result);
    Assert.assertEquals("7, 4, 2, 1, 3, 5, 9, 8, 11, 12", result);
  }

  @Test
  public void inorder() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    INT_STREAM.forEach(tree::add);

    BinaryTrees.println(tree);
    StringBuilder builder = new StringBuilder();
    tree.inorder(e -> builder.append(e).append(", "));
    String result = builder.delete(builder.length() - 2, builder.length()).toString();
    System.out.println(result);
    Assert.assertEquals("1, 2, 3, 4, 5, 7, 8, 9, 11, 12", result);
  }

  @Test
  public void postorder() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    INT_STREAM.forEach(tree::add);

    BinaryTrees.println(tree);

    StringBuilder builder = new StringBuilder();
    tree.postorder(e -> builder.append(e).append(", "));
    String result = builder.delete(builder.length() - 2, builder.length()).toString();

    System.out.println(result);
    Assert.assertEquals("1, 3, 2, 5, 4, 8, 12, 11, 9, 7", result);
  }

  @Test
  public void leverOrder() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    INT_STREAM.forEach(tree::add);

    BinaryTrees.println(tree);

    StringBuilder builder = new StringBuilder();
    tree.leverOrder(e -> builder.append(e).append(", "));
    String result = builder.delete(builder.length() - 2, builder.length()).toString();

    System.out.println(result);
    Assert.assertEquals("7, 4, 9, 2, 5, 8, 11, 1, 3, 12", result);
  }


  @Test
  public void testIsComplete() {
    Integer[] completed = new Integer[]{7, 4, 9, 2, 5};
    Integer[] notCompleted = new Integer[]{7, 4, 9, 2, 1};

    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    Arrays.stream(completed).forEach(tree::add);
    BinaryTrees.println(tree);
    Assert.assertTrue(tree.isComplete());

    tree.clear();
    Arrays.stream(notCompleted).forEach(tree::add);
    BinaryTrees.println(tree);
    Assert.assertFalse(tree.isComplete());
  }

  @Test
  public void removeBinarySearchTreeWithInteger() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    INT_STREAM.forEach(tree::add);
    tree.remove(7);
    BinaryTrees.println(tree);
    Assert.assertFalse(tree.contains(7));
  }


  @Test
  public void avlTreeAdd() {
    final AvlTree<Integer> tree = new AvlTree<>();
    int[] data = {85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56};
    Arrays.stream(data)
        .forEach(tree::add);
    BinaryTrees.println(tree);
    StringBuilder builder = new StringBuilder();
    tree.preorder(e -> builder.append(e).append(", "));
    String result = builder.delete(builder.length() - 2, builder.length()).toString();
    Assert.assertEquals("19, 7, 2, 1, 3, 4, 11, 14, 69, 44, 21, 57, 56, 58, 95, 85, 70, 93, 99", result);
  }

  @Test
  public void avlTreeRemove() {
    final AvlTree<Integer> tree = new AvlTree<>();
    int[] data = {85, 19, 69, 3, 7, 99, 95};
    Arrays.stream(data)
        .forEach(tree::add);
    tree.remove(99);
    tree.remove(85);
    tree.remove(95);
    BinaryTrees.println(tree);
    StringBuilder builder = new StringBuilder();
    tree.preorder(e -> builder.append(e).append(", "));
    String result = builder.delete(builder.length() - 2, builder.length()).toString();
    Assert.assertEquals("7, 3, 69, 19", result);
  }

  @Test
  public void redBlackTreeAdd() {
    final RedBlackTree<Integer> tree = new RedBlackTree<>();
    int[] data = {55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50};

    IntConsumer stepAdd = i -> {
      System.out.printf("[%s]%n", i);
      tree.add(i);
      BinaryTrees.print(tree);
      System.out.println("--------------------------");
    };

    Arrays.stream(data)
        .forEach(stepAdd);

    StringBuilder builder = new StringBuilder();
    tree.preorder(e -> builder.append(e).append(", "));
    String result = builder.delete(builder.length() - 2, builder.length()).toString();
    Assert.assertEquals("70, 56, 22, 20, 55, 50, 62, 68, 87, 74, 96, 90", result);
  }

  @Test
  public void redBlackTreeRemove() {
    final RedBlackTree<Integer> tree = new RedBlackTree<>();
    int[] data = {55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50};

    Arrays.stream(data)
        .forEach(tree::add);

    IntConsumer stepRemove = i -> {
      System.out.printf("[%s]%n", i);
      tree.remove(i);
      BinaryTrees.print(tree);
      System.out.println("--------------------------");
    };
    Arrays.stream(data)
        .forEach(stepRemove);

    Assert.assertTrue(tree.isEmpty());
  }

  @Test
  public void testTrie() {
    Trie<Integer> trie = new TrieImpl<>();
    trie.add("cat", 1);
    trie.add("dog", 2);
    trie.add("catalog", 3);
    trie.add("cast", 4);
    trie.add("linus", 5);

    Assert.assertEquals(5, trie.size());
    Assert.assertTrue(trie.startsWith("do"));
    Assert.assertTrue(trie.startsWith("c"));
    Assert.assertTrue(trie.startsWith("ca"));
    Assert.assertTrue(trie.startsWith("cat"));
    Assert.assertTrue(trie.startsWith("cata"));
    Assert.assertFalse(trie.startsWith("hehe"));
    Assert.assertEquals(5, (int) trie.get("linus"));
    Assert.assertEquals(1, (int) trie.remove("cat"));
    Assert.assertEquals(3, (int) trie.remove("catalog"));
    Assert.assertEquals(4, (int) trie.remove("cast"));
    Assert.assertEquals(2, trie.size());
    Assert.assertTrue(trie.startsWith("linu"));
    Assert.assertTrue(trie.startsWith("do"));
    Assert.assertFalse(trie.startsWith("c"));
    Assert.assertTrue(trie.contains("linus"));
    Assert.assertFalse(trie.contains("li"));
    trie.clear();
    Assert.assertEquals(0, trie.size());
  }

}
