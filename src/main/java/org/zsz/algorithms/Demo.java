package org.zsz.algorithms;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Linus Zhang
 * @create 2022-04-13 21:53
 */
@Slf4j
public class Demo {

  private Demo() {
  }

  public static String echo(String name) {
    return String.format("Hello, %s", name);
  }

}
