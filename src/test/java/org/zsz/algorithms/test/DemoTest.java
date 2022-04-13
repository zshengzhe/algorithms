package org.zsz.algorithms.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zsz.algorithms.Demo;

/**
 * @author Linus Zhang
 * @create 2022-04-13 21:57
 */
@Slf4j
public class DemoTest {

  @Test
  public void testEcho() {
    String linus = Demo.echo("Linus");
    log.info("echo -> {}", linus);
  }

}
