package org.zsz.algorithms.leetcode;

import com.google.common.base.Preconditions;
import java.util.Objects;

/**
 * 237. 删除链表中的节点
 * <p>
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 *
 * @author Linus Zhang
 * @create 2022-04-14 23:17
 */
public class Solution237 {

  public void deleteNode(ListNode node) {
    Preconditions.checkArgument(Objects.nonNull(node) && Objects.nonNull(node.next));
    ListNode next = node.next;
    node.val = next.val;
    node.next = next.next;
  }

  private static class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
    }
  }

}
