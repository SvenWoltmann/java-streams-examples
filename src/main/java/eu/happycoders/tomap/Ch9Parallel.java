package eu.happycoders.tomap;

import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.IntStream;

public class Ch9Parallel {

  static void main() {
    System.out.println("== toMap(), parallel, the later number wins");
    for (int run = 0; run < 5; run++) {
      Map<Integer, Integer> lastByRemainder =
          IntStream.range(0, 100_000)
              .boxed()
              .parallel()
              .collect(toMap(i -> i % 3, i -> i, (first, second) -> second));
      System.out.println(lastByRemainder);
    }

    System.out.println("== toConcurrentMap(), parallel, the later number wins");
    for (int run = 0; run < 5; run++) {
      Map<Integer, Integer> lastByRemainder =
          IntStream.range(0, 100_000)
              .boxed()
              .parallel()
              .collect(toConcurrentMap(i -> i % 3, i -> i, (first, second) -> second));
      System.out.println(lastByRemainder);
    }
  }
}
