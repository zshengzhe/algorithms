package org.zsz.algorithms.leetcode;

/**
 * 203. 移除链表元素
 * <p>
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 *
 * @author Linus Zhang
 * @create 2022-04-24 23:58
 */
public class Solution203 {

  public ListNode removeElements(ListNode head, int val) {
    ListNode newHead = new ListNode();
    ListNode current = newHead;
    while (head != null) {
      if (val != head.val) {
        current.next = new ListNode(head.val);
        current = current.next;
      }
      head = head.next;
    }
    return newHead.next;
  }

  private static class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int x) {
      val = x;
      next = null;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }

  }

}
