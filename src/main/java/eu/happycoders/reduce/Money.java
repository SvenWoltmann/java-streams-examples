package eu.happycoders.reduce;

/**
 * An immutable amount of money in cents: add() returns a new object instead of changing this one.
 */
public record Money(long cents) {

  public static final Money ZERO = new Money(0);

  public Money add(Money other) {
    return new Money(cents + other.cents);
  }
}
