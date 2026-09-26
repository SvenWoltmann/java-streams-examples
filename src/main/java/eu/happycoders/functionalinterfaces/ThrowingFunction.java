package eu.happycoders.functionalinterfaces;

import java.util.function.Function;

/**
 * A {@code Function} whose {@code apply()} may throw a checked exception. The JDK has no such
 * interface: a lambda may only throw what its functional method declares, and the methods in {@code
 * java.util.function} declare nothing.
 *
 * @param <T> the type of the input
 * @param <R> the type of the result
 * @param <E> the checked exception the function may throw
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

  R apply(T t) throws E;

  /**
   * Adapts a throwing function to a plain {@code Function}, so it can be passed to the Stream API.
   * A checked exception is wrapped in an {@code IllegalStateException} whose cause is the original
   * exception; a runtime exception passes through unchanged.
   */
  static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R, ?> function) {
    return t -> {
      try {
        return function.apply(t);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    };
  }
}
