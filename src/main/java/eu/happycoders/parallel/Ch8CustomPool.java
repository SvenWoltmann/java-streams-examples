package eu.happycoders.parallel;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ch8CustomPool {

  static void main() throws InterruptedException, ExecutionException {
    System.out.println("== A parallel stream started inside a pool of its own");
    try (ForkJoinPool pool = new ForkJoinPool(4)) {
      Set<String> threadNames =
          pool.submit(
                  () ->
                      IntStream.range(0, 1_000_000)
                          .parallel()
                          .mapToObj(i -> Thread.currentThread().getName())
                          .collect(Collectors.toSet()))
              .get();
      System.out.println(threadNames);
    }
  }
}
