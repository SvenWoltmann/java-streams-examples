package eu.happycoders.parallel.benchmark;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/** Merge cost of the collectors: the same source, with and without work per element. */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(3)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class CollectorBenchmark {

  static final int SIZE = 1_000_000;

  @Param({"false", "true"})
  boolean parallel;

  @Param({"0", "100"})
  int tokens;

  List<Integer> list;

  @Setup
  public void setup() {
    list = new ArrayList<>(IntStream.range(0, SIZE).boxed().toList());
  }

  private Stream<Integer> stream() {
    Stream<Integer> s = parallel ? list.parallelStream() : list.stream();
    return s.map(this::work);
  }

  @Benchmark
  public List<Integer> toList() {
    return stream().toList();
  }

  @Benchmark
  public Set<Integer> toSet() {
    return stream().collect(Collectors.toSet());
  }

  @Benchmark
  public Map<Integer, Integer> toMap() {
    return stream().collect(Collectors.toMap(i -> i, i -> i));
  }

  @Benchmark
  public Map<Integer, Integer> toConcurrentMap() {
    return stream().collect(Collectors.toConcurrentMap(i -> i, i -> i));
  }

  @Benchmark
  public Map<Integer, List<Integer>> groupingBy() {
    return stream().collect(Collectors.groupingBy(i -> i % 1000));
  }

  @Benchmark
  public Map<Integer, List<Integer>> groupingByConcurrent() {
    return stream().collect(Collectors.groupingByConcurrent(i -> i % 1000));
  }

  @Benchmark
  public String joining() {
    return stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  private Integer work(Integer i) {
    Blackhole.consumeCPU(tokens);
    return i;
  }
}
