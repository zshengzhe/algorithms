package org.zsz.algorithms.support;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * @author Linus Zhang
 * @create 2022-05-23 21:42
 */
public final class TypeSupport {

  private TypeSupport() {
  }

  public static <T> T returnNull() {
    return null;
  }

  @SuppressWarnings("unchecked")
  public static <T> T unsafeCast(Object obj) {
    return (T) obj;
  }

  public static <T> T cast(Object obj, Class<T> cls) {
    return TypeUtils.cast(obj, cls, ParserConfig.getGlobalInstance());
  }
}
