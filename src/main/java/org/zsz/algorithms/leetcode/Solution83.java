package org.zsz.algorithms.leetcode;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.zsz.algorithms.support.ListNodeBuilder;

/**
 * 83. 删除排序链表中的重复元素
 * <p>
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 *
 * @author Linus Zhang
 * @create 2022-04-25 00:03
 */
@Slf4j
public class Solution83 {

  public static void main(String[] args) {
    ListNode l = ListNodeBuilder.create(ListNode.class).build("1,1,2,3,3");
    Solution83 s = new Solution83();
    log.info("ListNode -> {}", s.deleteDuplicates(l));
  }

  public ListNode deleteDuplicates(ListNode head) {
    if (Objects.isNull(head)) {
      return head;
    }

    ListNode newHead = new ListNode(head.val);
    ListNode current = newHead;
    while (Objects.nonNull(head)) {
      if (current.val != head.val) {
        current.next = new ListNode(head.val);
        current = current.next;
      }
      head = head.next;
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
