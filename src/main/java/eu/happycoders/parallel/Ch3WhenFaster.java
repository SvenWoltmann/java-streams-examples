package eu.happycoders.parallel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * How the sources split. The measurements of this chapter are JMH benchmarks; they are in the
 * directory benchmarks/parallel-streams of this repository.
 */
public class Ch3WhenFaster {

  static void main() {
    System.out.println("== trySplit() on a LinkedList splits off growing blocks");
    List<Integer> numbers = new LinkedList<>(IntStream.range(0, 1_000_000).boxed().toList());
    Spliterator<Integer> spliterator = numbers.spliterator();
    for (int i = 0; i < 5; i++) {
      Spliterator<Integer> part = spliterator.trySplit();
      System.out.println(part.estimateSize());
    }
    System.out.println("remaining: " + spliterator.estimateSize());

    System.out.println("== Pieces of one million elements, split the way the stream does");
    List<Integer> values = IntStream.range(0, 1_000_000).boxed().toList();
    printPieces("ArrayList", new ArrayList<>(values).spliterator());
    printPieces("HashSet", new HashSet<>(values).spliterator());
    printPieces("TreeSet", new TreeSet<>(values).spliterator());
  }

  private static void printPieces(String name, Spliterator<Integer> spliterator) {
    List<Long> sizes = Pieces.actualSizes(spliterator, Pieces.targetSize(1_000_000));
    LongSummaryStatistics stats = sizes.stream().mapToLong(Long::longValue).summaryStatistics();
    System.out.printf(
        "%-9s %d pieces, smallest %d, largest %d, average %.0f%n",
        name, stats.getCount(), stats.getMin(), stats.getMax(), stats.getAverage());
  }
}
