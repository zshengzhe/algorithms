package org.zsz.algorithms.support;

import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;

/**
 * OptionalString <br/> 采用 StringUtils::isBlank 判空
 *
 * @author Zhang Shengzhe
 * @create 2021-09-13 15:11
 */
public final class OptionalString {

  private static final OptionalString EMPTY = new OptionalString();

  /**
   * If true then the value is present, otherwise indicates no value is present
   */
  private final boolean isPresent;
  private final String value;

  /**
   * Construct an empty instance.
   */
  private OptionalString() {
    this.isPresent = false;
    this.value = null;
  }

  /**
   * Returns an empty {@code OptionalString} instance.  No value is present for this OptionalString.
   *
   * @return an empty {@code OptionalString}
   * @apiNote Though it may be tempting to do so, avoid testing if an object is empty by comparing with {@code ==} against instances returned by
   * {@code Option.empty()}. There is no guarantee that it is a singleton. Instead, use {@link #isPresent()}.
   */
  public static OptionalString empty() {
    return EMPTY;
  }

  /**
   * Construct an instance with the value present.
   *
   * @param value the String value to be present
   */
  private OptionalString(String value) {
    Preconditions.checkArgument(StringUtils.isNotBlank(value), "value is null");
    this.isPresent = true;
    this.value = value;
  }

  /**
   * Return an {@code OptionalString} with the specified value present.
   *
   * @param value the value to be present
   * @return an {@code OptionalString} with the value present
   */
  public static OptionalString of(String value) {
    return new OptionalString(value);
  }

  public static OptionalString ofNullable(String value) {
    return StringUtils.isBlank(value) ? empty() : of(value);
  }

  /**
   * If a value is present in this {@code OptionalString}, returns the value, otherwise throws {@code NoSuchElementException}.
   *
   * @return the value held by this {@code OptionalString}
   * @throws NoSuchElementException if there is no value present
   * @see OptionalString#isPresent()
   */
  public String get() {
    if (!isPresent) {
      throw new NoSuchElementException("No value present");
    }
    return value;
  }

  /**
   * Return {@code true} if there is a value present, otherwise {@code false}.
   *
   * @return {@code true} if there is a value present, otherwise {@code false}
   */
  public boolean isPresent() {
    return isPresent;
  }

  /**
   * Have the specified consumer accept the value if a value is present, otherwise do nothing.
   *
   * @param consumer block to be executed if a value is present
   * @throws NullPointerException if value is present and {@code consumer} is null
   */
  public void ifPresent(Consumer<String> consumer) {
    if (isPresent) {
      consumer.accept(value);
    }
  }

  public <U> Optional<U> map(Function<String, ? extends U> mapper) {
    Objects.requireNonNull(mapper);
    return isPresent() ? Optional.ofNullable(mapper.apply(value)) : Optional.empty();
  }

  /**
   * Return the value if present, otherwise return {@code other}.
   *
   * @param other the value to be returned if there is no value present
   * @return the value, if present, otherwise {@code other}
   */
  public String orElse(String other) {
    return isPresent ? value : other;
  }

  /**
   * Return the value if present, otherwise invoke {@code other} and return the result of that invocation.
   *
   * @param other a {@code Supplier} whose result is returned if no value is present
   * @return the value if present otherwise the result of {@code other.get()}
   * @throws NullPointerException if value is not present and {@code other} is null
   */
  public String orElseGet(Supplier<String> other) {
    return isPresent ? value : other.get();
  }

  /**
   * Return the contained value, if present, otherwise throw an exception to be created by the provided supplier.
   *
   * @param <X>               Type of the exception to be thrown
   * @param exceptionSupplier The supplier which will return the exception to be thrown
   * @return the present value
   * @throws X                    if there is no value present
   * @throws NullPointerException if no value is present and {@code exceptionSupplier} is null
   * @apiNote A method reference to the exception constructor with an empty argument list can be used as the supplier. For example, {@code
   * IllegalStateException::new}
   */
  public <X extends Throwable> String orElseThrow(Supplier<X> exceptionSupplier) throws X {
    if (isPresent) {
      return value;
    } else {
      throw exceptionSupplier.get();
    }
  }

  /**
   * Indicates whether some other object is "equal to" this OptionalString. The other object is considered equal if:
   * <ul>
   * <li>it is also an {@code OptionalString} and;
   * <li>both instances have no value present or;
   * <li>the present values are "equal to" each other via {@code ==}.
   * </ul>
   *
   * @param obj an object to be tested for equality
   * @return {code true} if the other object is "equal to" this object otherwise {@code false}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof OptionalString)) {
      return false;
    }

    OptionalString other = (OptionalString) obj;
    return (isPresent && other.isPresent)
        ? StringUtils.equals(value, other.value)
        : isPresent == other.isPresent;
  }

  /**
   * Returns the hash code value of the present value, if any, or 0 (zero) if no value is present.
   *
   * @return hash code value of the present value or 0 if no value is present
   */
  @Override
  public int hashCode() {
    return isPresent ? value.hashCode() : 0;
  }

  /**
   * {@inheritDoc}
   * <p>
   * Returns a non-empty string representation of this object suitable for debugging. The exact presentation format is unspecified and may vary
   * between implementations and versions.
   *
   * @return the string representation of this instance
   * @implSpec If a value is present the result must include its string representation in the result. Empty and present instances must be
   * unambiguously differentiable.
   */
  @Override
  public String toString() {
    return isPresent
        ? String.format("OptionalString[%s]", value)
        : "OptionalString.empty";
  }
}
