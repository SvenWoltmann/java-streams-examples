package eu.happycoders.reduce;

import java.util.stream.IntStream;

public class Ch1HowItWorks {

  static void main() {
    System.out.println("== reduce() sums the numbers 1 to 5");
    int sum = IntStream.rangeClosed(1, 5).reduce(0, (a, b) -> a + b);
    System.out.println(sum);

    System.out.println("== The same as a loop");
    int result = 0;
    for (int element = 1; element <= 5; element++) {
      result = result + element;
    }
    System.out.println(result);

    System.out.println("== Every call of the accumulator");
    int traced =
        IntStream.rangeClosed(1, 5)
            .reduce(
                0,
                (a, b) -> {
                  System.out.println(a + " + " + b + " = " + (a + b));
                  return a + b;
                });
    System.out.println(traced);
  }
}
