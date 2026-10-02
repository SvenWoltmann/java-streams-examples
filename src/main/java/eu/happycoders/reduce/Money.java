package eu.happycoders.reduce;

import java.math.BigDecimal;

/** An immutable amount of money: add() returns a new object instead of changing this one. */
public record Money(BigDecimal amount) {

  public static final Money ZERO = new Money(BigDecimal.ZERO);

  public static Money of(String amount) {
    return new Money(new BigDecimal(amount));
  }

  public Money add(Money other) {
    return new Money(amount.add(other.amount));
  }
}
