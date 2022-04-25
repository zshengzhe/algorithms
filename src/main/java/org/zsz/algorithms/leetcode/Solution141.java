package org.zsz.algorithms.leetcode;

import java.util.Objects;

/**
 * 141. 环形链表
 * <p>
 * https://leetcode-cn.com/problems/linked-list-cycle/
 *
 * @author Linus Zhang
 * @create 2022-04-18 23:57
 */
public class Solution141 {

  public boolean hasCycle(ListNode head) {
    if (Objects.isNull(head) || Objects.isNull(head.next)) {
      return false;
    }

    ListNode slowP = head;
    ListNode fastP = head.next;
    while (Objects.nonNull(fastP) && Objects.nonNull(fastP.next)) {
      if (slowP == fastP) {
        return true;
      }
      slowP = slowP.next;
      fastP = fastP.next.next;
    }
    return false;
  }

  private static class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
      next = null;
    }
  }

}
