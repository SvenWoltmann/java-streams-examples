package eu.happycoders.optional;

import java.util.Optional;

/**
 * Self-contained, so that it can also be run with a Valhalla early-access build: {@code java
 * --enable-preview --source 27 Ch8ValueBased.java}.
 */
public class Ch8ValueBased {

  static void main() {
    Optional<String> a = Optional.of("Dracula");
    Optional<String> b = Optional.of("Dracula");

    System.out.println("== equals() vs. ==");
    System.out.println(a.equals(b));
    System.out.println(a == b);

    System.out.println("== two equal but distinct strings");
    Optional<String> c = Optional.of(new String("Dracula"));
    Optional<String> d = Optional.of(new String("Dracula"));
    System.out.println(c.equals(d));
    System.out.println(c == d);

    System.out.println("== synchronizing on an Optional");
    Object lock = a;
    try {
      synchronized (lock) {
        System.out.println("locked");
      }
    } catch (RuntimeException e) {
      System.out.println(e);
    }
  }
}
