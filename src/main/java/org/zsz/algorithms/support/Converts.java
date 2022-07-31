package org.zsz.algorithms.support;

import com.google.common.base.Preconditions;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

/**
 * 转换工具类
 *
 * @author Zhang Shengzhe
 * @create 2021-01-21 11:31
 */
public final class Converts {

  private Converts() {
  }

  public static <E extends Enum<E>> String description(E e, Function<E, String> function) {
    return convert(e, function);
  }

  public static <T, R> R convert(T src, Function<T, R> function) {
    return convert(src, null, function);
  }

  public static <T, R> R convert(T src, R defaultVal, Function<T, R> function) {
    return convert(Objects::isNull, src, defaultVal, function);
  }

  public static <T, R> R convert(Predicate<T> nullPredicate, T src, R defaultVal, Function<T, R> converter) {
    if (Objects.isNull(nullPredicate)) {
      nullPredicate = Objects::isNull;
    }

    if (nullPredicate.test(src)) {
      return defaultVal;
    }
    Preconditions.checkNotNull(converter, "Converts.safeConvert converter is null");
    return converter.apply(src);
  }

  public static String takeMsg(Exception e) {
    return Converts.convert(StringUtils::isBlank, e.getMessage(), "未知错误", Function.identity());
  }

}