package eu.happycoders.parallel;

import static eu.happycoders.streams.Library.BOOKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * Run it with {@code -XX:ActiveProcessorCount=1} to see the common pool with a single worker, or
 * with {@code -Djava.util.concurrent.ForkJoinPool.common.parallelism=4} to see four workers.
 */
public class Ch2HowItWorks {

  static void main() {
    System.out.println("== trySplit() on an ArrayList halves it");
    List<Integer> numbers = new ArrayList<>(IntStream.range(0, 1_000_000).boxed().toList());
    Spliterator<Integer> spliterator = numbers.spliterator();
    for (int i = 0; i < 5; i++) {
      Spliterator<Integer> part = spliterator.trySplit();
      System.out.println(part.estimateSize());
    }
    System.out.println("remaining: " + spliterator.estimateSize());

    System.out.println("== The pieces a parallel stream makes of the same list");
    long targetSize = Pieces.targetSize(numbers.size());
    List<Long> sizes = Pieces.actualSizes(numbers.spliterator(), targetSize);
    System.out.println("target size " + targetSize + ", " + sizes.size() + " pieces");
    System.out.println("piece sizes: " + new TreeSet<>(sizes));

    System.out.println("== Which thread processes which book (order and threads vary)");
    BOOKS.parallelStream()
        .forEach(
            book -> System.out.println(Thread.currentThread().getName() + ": " + book.title()));

    System.out.println("== Processors and common pool parallelism");
    System.out.println(Runtime.getRuntime().availableProcessors());
    System.out.println(ForkJoinPool.getCommonPoolParallelism());

    System.out.println("== Number of threads that process a parallel stream");
    Set<String> threadNames = ConcurrentHashMap.newKeySet();
    IntStream.range(0, 1_000_000)
        .parallel()
        .forEach(i -> threadNames.add(Thread.currentThread().getName()));
    System.out.println(threadNames.size());
  }
}
