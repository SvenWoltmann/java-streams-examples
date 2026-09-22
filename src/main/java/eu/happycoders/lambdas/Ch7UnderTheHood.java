package eu.happycoders.lambdas;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class Ch7UnderTheHood {

  static void main() {
    System.out.println("== the generated class (see also LambdaDemo)");
    LambdaDemo.main(new String[0]);

    System.out.println("== a non-capturing lambda is created once");
    Supplier<String> first = nonCapturing();
    Supplier<String> second = nonCapturing();
    System.out.println(first == second);
    System.out.println(first.equals(second));

    System.out.println("== a capturing lambda is a new object each time");
    Predicate<Integer> greaterThanFive = greaterThan(5);
    Predicate<Integer> greaterThanFiveAgain = greaterThan(5);
    System.out.println(greaterThanFive == greaterThanFiveAgain);
    System.out.println(greaterThanFive.equals(greaterThanFiveAgain));
  }

  static Supplier<String> nonCapturing() {
    return () -> "constant";
  }

  static Predicate<Integer> greaterThan(int n) {
    return x -> x > n;
  }
}
