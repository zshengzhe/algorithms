package org.zsz.algorithms.support;

import com.google.common.base.Preconditions;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 可以帮助构建ListNode
 * <p>
 * 需要Node中存在val和next字段
 *
 * @author Linus Zhang
 * @create 2022-04-26 00:20
 */
public class ListNodeBuilder<T> {

  private static final String COMMA = ",";

  private static final String CREATE_BUILDER_ERROR = "Create ListNodeBuilder Error";

  private static final String BUILD_ERROR = "Build ListNode Error";

  private final Constructor<T> constructor;

  private final Field valField;

  private final Field nextField;

  private ListNodeBuilder(Class<T> struct) throws NoSuchFieldException, NoSuchMethodException {
    constructor = struct.getDeclaredConstructor(null);
    constructor.setAccessible(true);
    valField = struct.getDeclaredField("val");
    valField.setAccessible(true);
    nextField = struct.getDeclaredField("next");
    nextField.setAccessible(true);
  }

  public static <T> ListNodeBuilder<T> create(Class<T> struct) {
    Preconditions.checkNotNull(struct, "ListNodeBuilder need a struct");
    try {
      return new ListNodeBuilder(struct);
    } catch (Exception e) {
      throw new RuntimeException(CREATE_BUILDER_ERROR, e);
    }
  }

  public T build(String nums) {
    String[] split = nums.split(COMMA);
    Function<String, Integer> strToInt = str -> {
      Preconditions.checkArgument(NumberUtils.isCreatable(str));
      return Integer.valueOf(str);
    };
    List<Integer> list = Arrays.stream(split)
        .map(StringUtils::trim)
        .map(strToInt)
        .collect(Collectors.toList());
    try {
      return build(list);
    } catch (Exception e) {
      throw new RuntimeException(BUILD_ERROR, e);
    }
  }

  public T build(List<Integer> nums) {
    try {
      return build(nums.stream().mapToInt(i -> i).toArray());
    } catch (Exception e) {
      throw new RuntimeException(BUILD_ERROR, e);
    }
  }

  public T build(int[] nums) {
    try {
      return doBuild(nums);
    } catch (Exception e) {
      throw new RuntimeException(BUILD_ERROR, e);
    }
  }

  private T doBuild(int[] nums) throws InstantiationException, IllegalAccessException, InvocationTargetException {
    T head = null;
    T current = null;
    for (int num : nums) {
      T node = build(num);
      if (Objects.isNull(head)) {
        head = node;
        current = head;
        continue;
      }

      nextField.set(current, node);
      current = node;
    }
    return head;
  }

  private T build(int num) throws InstantiationException, IllegalAccessException, InvocationTargetException {
    T listNode = constructor.newInstance();
    valField.set(listNode, num);
    return listNode;
  }


}
