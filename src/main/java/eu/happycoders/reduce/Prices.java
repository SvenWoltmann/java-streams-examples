package eu.happycoders.reduce;

import java.math.BigDecimal;
import java.util.List;

/** Example amounts for the BigDecimal sum - not tied to the books of the library. */
public class Prices {

  private Prices() {}

  public static final List<BigDecimal> ORDER =
      List.of(new BigDecimal("12.99"), new BigDecimal("8.50"), new BigDecimal("14.95"));
}
