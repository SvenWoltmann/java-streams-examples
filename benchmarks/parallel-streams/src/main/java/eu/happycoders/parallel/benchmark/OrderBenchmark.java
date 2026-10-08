package eu.happycoders.parallel.benchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/** Operations whose cost depends on the encounter order, sequential vs. parallel. */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(3)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class OrderBenchmark {

  static final int SIZE = 1_000_000;
  static final int TOKENS = 100;

  @Param({"false", "true"})
  boolean parallel;

  List<Integer> list;

  // the same numbers in a fixed random order, so that sorted() has something to sort
  List<Integer> shuffled;

  @Setup
  public void setup() {
    list = new ArrayList<>(IntStream.range(0, SIZE).boxed().toList());
    shuffled = new ArrayList<>(list);
    Collections.shuffle(shuffled, new Random(42));
  }

  private Stream<Integer> stream() {
    return parallel ? list.parallelStream() : list.stream();
  }

  private Stream<Integer> shuffledStream() {
    return parallel ? shuffled.parallelStream() : shuffled.stream();
  }

  // the match lies in the second half: findFirst must deliver the first match in encounter order
  @Benchmark
  public Optional<Integer> findFirst() {
    return stream().filter(i -> work(i) && i >= SIZE / 2).findFirst();
  }

  @Benchmark
  public Optional<Integer> findAny() {
    return stream().filter(i -> work(i) && i >= SIZE / 2).findAny();
  }

  @Benchmark
  public List<Integer> limitOrdered() {
    return stream().filter(this::work).limit(1000).toList();
  }

  @Benchmark
  public List<Integer> limitUnordered() {
    return stream().unordered().filter(this::work).limit(1000).toList();
  }

  @Benchmark
  public List<Integer> forEachOrderedCollect() {
    List<Integer> out = new ArrayList<>();
    stream().filter(this::work).forEachOrdered(out::add);
    return out;
  }

  @Benchmark
  public List<Integer> sorted() {
    return stream().filter(this::work).sorted().toList();
  }

  @Benchmark
  public List<Integer> distinct() {
    return stream().filter(this::work).map(i -> i % 1000).distinct().toList();
  }

  // sorted() on numbers in random order: with work per element, and with the sort alone
  @Benchmark
  public List<Integer> sortedShuffled() {
    return shuffledStream().filter(this::work).sorted().toList();
  }

  @Benchmark
  public List<Integer> sortedShuffledWithoutWork() {
    return shuffledStream().sorted().toList();
  }

  // distinct() with 100,000 different values, each occurring ten times
  @Benchmark
  public List<Integer> distinctMod100k() {
    return stream().filter(this::work).map(i -> i % 100_000).distinct().toList();
  }

  private boolean work(int i) {
    Blackhole.consumeCPU(TOKENS);
    return true;
  }
}
