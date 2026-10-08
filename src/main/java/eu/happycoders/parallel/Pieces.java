package eu.happycoders.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;

/**
 * Splits a spliterator the way a parallel stream does - halving with {@code trySplit()} until the
 * size estimate is at most the target size - and counts the elements that really end up in each
 * piece.
 */
final class Pieces {

  private Pieces() {}

  /** The target size of AbstractTask.suggestTargetSize(): size ÷ (4 × common pool parallelism). */
  static long targetSize(long size) {
    return Math.max(1, size / (ForkJoinPool.getCommonPoolParallelism() * 4L));
  }

  static List<Long> actualSizes(Spliterator<?> spliterator, long targetSize) {
    List<Long> sizes = new ArrayList<>();
    split(spliterator, targetSize, sizes);
    return sizes;
  }

  private static void split(Spliterator<?> spliterator, long targetSize, List<Long> sizes) {
    Spliterator<?> left;
    if (spliterator.estimateSize() <= targetSize || (left = spliterator.trySplit()) == null) {
      long[] count = {0};
      spliterator.forEachRemaining(_ -> count[0]++);
      sizes.add(count[0]);
      return;
    }
    split(left, targetSize, sizes);
    split(spliterator, targetSize, sizes);
  }
}
