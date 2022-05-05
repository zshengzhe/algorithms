package org.zsz.algorithms.leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 20. 有效的括号
 * <p>
 * https://leetcode-cn.com/problems/valid-parentheses/
 *
 * @author Linus Zhang
 * @create 2022-04-26 23:15
 */
public class Solution20 {

  private static final Map<Character, Character> MAPPING = new HashMap<>();

  static {
    MAPPING.put(')', '(');
    MAPPING.put(']', '[');
    MAPPING.put('}', '{');
  }

  public boolean isValid(String s) {
    Deque<Character> stack = new LinkedList<>();

    if (s.length() <= 1) {
      return false;
    }

    for (char c : s.toCharArray()) {

      if (MAPPING.containsValue(c)) {
        stack.push(c);
        continue;
      }

      if (MAPPING.containsKey(c)) {
        if (stack.isEmpty() || !MAPPING.get(c).equals(stack.pop())) {
          return false;
        }
      }
    }

    return stack.isEmpty();
  }

}
