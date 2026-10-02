package eu.happycoders.reduce;

import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class Ch6Fold {

  static void main() {
    System.out.println("== fold(): digits to a number, also in a parallel stream");
    int number =
        IntStream.rangeClosed(1, 5)
            .boxed()
            .parallel()
            .gather(Gatherers.fold(() -> 0, (a, b) -> 10 * a + b))
            .findFirst()
            .orElseThrow();
    System.out.println(number);
  }
}
