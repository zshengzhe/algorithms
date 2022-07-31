package org.zsz.algorithms.support;

import com.google.common.base.Stopwatch;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Linus Zhang
 * @create 2022-07-30 20:05
 */
@Slf4j
public class Times {

  private Times() {
  }


  public static <V> V timing(String action, Callable<V> task) {
    if (Objects.isNull(task)) {
      return null;
    }

    String title = OptionalString.ofNullable(action)
        .map(s -> "【" + s + "】")
        .orElse(StringUtils.EMPTY);
    log.info("Timing {}", title);

    Stopwatch stopwatch = Stopwatch.createStarted();
    V result = null;
    try {
      result = task.call();
    } catch (Exception e) {
      log.error("发送异常: {}", Converts.takeMsg(e), e);
    }
    long timeCost = stopwatch.elapsed(TimeUnit.MILLISECONDS);
    log.info("COST: {}ms", timeCost);
    return result;
  }

}
