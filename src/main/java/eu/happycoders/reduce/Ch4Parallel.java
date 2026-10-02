package eu.happycoders.reduce;

import java.util.List;
import java.util.stream.IntStream;

public class Ch4Parallel {

  static void main() {
    System.out.println("== Sequential and parallel: the same sum");
    System.out.println(IntStream.rangeClosed(1, 5).reduce(0, Integer::sum));
    System.out.println(IntStream.rangeClosed(1, 5).parallel().reduce(0, Integer::sum));

    System.out.println("== Which partial results the combiner joins (order may vary)");
    int traced =
        IntStream.rangeClosed(1, 5)
            .boxed()
            .parallel()
            .reduce(
                0,
                (a, b) -> {
                  System.out.println("accumulate " + a + " + " + b + " = " + (a + b));
                  return a + b;
                },
                (a, b) -> {
                  System.out.println("combine    " + a + " + " + b + " = " + (a + b));
                  return a + b;
                });
    System.out.println(traced);

    System.out.println("== Identity 10 is not neutral");
    System.out.println(IntStream.rangeClosed(1, 5).reduce(10, Integer::sum));
    System.out.println(IntStream.rangeClosed(1, 5).parallel().reduce(10, Integer::sum));

    System.out.println("== Fix: add the 10 outside of reduce()");
    System.out.println(10 + IntStream.rangeClosed(1, 5).parallel().reduce(0, Integer::sum));

    System.out.println("== Digits to a number: the accumulator is not associative");
    System.out.println(IntStream.rangeClosed(1, 5).reduce(0, (a, b) -> 10 * a + b));
    System.out.println(IntStream.rangeClosed(1, 5).parallel().reduce(0, (a, b) -> 10 * a + b));

    System.out.println("== Sum of squares with two arguments");
    List<Integer> numbers = List.of(1, 2, 3, 4, 5);
    System.out.println(numbers.stream().reduce(0, (sum, x) -> sum + x * x));
    System.out.println(numbers.parallelStream().reduce(0, (sum, x) -> sum + x * x));

    System.out.println("== Sum of squares with a combiner");
    System.out.println(numbers.parallelStream().reduce(0, (sum, x) -> sum + x * x, Integer::sum));

    System.out.println("== Sum of squares with map() and reduce()");
    System.out.println(numbers.parallelStream().map(x -> x * x).reduce(0, Integer::sum));
  }
}
