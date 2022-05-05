package org.zsz.algorithms.leetcode;

import java.util.Stack;

/**
 * 232. 用栈实现队列
 * <p>
 * https://leetcode-cn.com/problems/implement-queue-using-stacks/
 *
 * @author Linus Zhang
 * @create 2022-04-26 23:32
 */
public class Solution232 {

  public static class MyQueue {

    private final Stack<Integer> input;

    private final Stack<Integer> output;

    public MyQueue() {
      input = new Stack<>();
      output = new Stack<>();
    }

    public void push(int x) {
      input.push(x);
    }

    public int pop() {
      if (output.isEmpty()) {
        inToOut();
      }
      return output.pop();
    }

    public int peek() {
      if (output.isEmpty()) {
        inToOut();
      }
      return output.peek();
    }

    public boolean empty() {
      return input.isEmpty() && output.isEmpty();
    }

    private void inToOut() {
      while (!input.isEmpty()) {
        output.push(input.pop());
      }
    }

  }

}
