package org.zsz.algorithms.leetcode;

import java.util.Objects;

/**
 * 206. 反转链表
 * <p>
 * https://leetcode-cn.com/problems/reverse-linked-list/
 *
 * @author Linus Zhang
 * @create 2022-04-14 23:21
 */
public class Solution206 {

  /**
   * 递归
   *
   * @param head
   * @return
   */
  public ListNode reverseListRecursion(ListNode head) {
    if (Objects.isNull(head) || Objects.isNull(head.next)) {
      return head;
    }
    /**
     * 1 -> 2 -> 3 -> 4 -> null
     *
     * 递归后
     * newHead = 4 -> 3 -> 2 -> null
     *
     * head = 1 -> 2 -> null
     *
     * currentLast = 2 -> null
     */
    ListNode newHead = reverseListRecursion(head.next);
    ListNode currentLast = head.next;
    // reverse head & currentLast
    head.next = currentLast.next;
    currentLast.next = head;

    return newHead;

  }

  /**
   * 迭代
   *
   * @param head
   * @return
   */
  public ListNode reverseListIterate(ListNode head) {
    if (Objects.isNull(head) || Objects.isNull(head.next)) {
      return head;
    }
    // 头节点
    ListNode newHead = head;
    ListNode curr = head.next;
    newHead.next = null;
    /**
     * 1 -> 2 -> 3 -> 4 -> null
     *
     * newHead = 1 -> null
     * curr = 2 -> 3 -> 4 -> null
     *
     * next = 3 -> 4 -> null
     * curr.next = newHead 2 -> 1 -> null
     */
    while (Objects.nonNull(curr)) {
      ListNode next = curr.next;

      curr.next = newHead;
      newHead = curr;

      curr = next;
    }
    return newHead;
  }

  private static class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

}
