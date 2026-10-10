package eu.happycoders.groupingby;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.groupingByConcurrent;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Ch8Parallel {

  static void main() {
    for (int run = 0; run < 5; run++) {
      Map<Integer, List<Integer>> byRemainder =
          IntStream.range(0, 100_000).boxed().parallel().collect(groupingBy(i -> i % 3));
      Map<Integer, List<Integer>> byRemainderConcurrent =
          IntStream.range(0, 100_000).boxed().parallel().collect(groupingByConcurrent(i -> i % 3));
      System.out.println(
          byRemainder.get(0).subList(0, 5) + "  " + byRemainderConcurrent.get(0).subList(0, 5));
    }
  }
}
