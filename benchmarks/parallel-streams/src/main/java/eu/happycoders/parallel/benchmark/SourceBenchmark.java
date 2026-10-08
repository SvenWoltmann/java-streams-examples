package eu.happycoders.parallel.benchmark;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/** The same pipeline (medium work per element, sum) over sources that split differently. */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(3)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class SourceBenchmark {

  static final int SIZE = 1_000_000;
  static final int TOKENS = 100;

  @Param({"arraylist", "linkedlist", "hashset", "treeset", "iterate", "lines"})
  String source;

  List<Integer> arrayList;
  List<Integer> linkedList;
  Set<Integer> hashSet;
  Set<Integer> treeSet;
  String text;

  @Setup
  public void setup() {
    List<Integer> numbers = IntStream.range(0, SIZE).boxed().toList();
    arrayList = new ArrayList<>(numbers);
    linkedList = new LinkedList<>(numbers);
    hashSet = new HashSet<>(numbers);
    treeSet = new TreeSet<>(numbers);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < SIZE; i++) sb.append(i).append('\n');
    text = sb.toString();
  }

  private Stream<Integer> stream() {
    return switch (source) {
      case "arraylist" -> arrayList.stream();
      case "linkedlist" -> linkedList.stream();
      case "hashset" -> hashSet.stream();
      case "treeset" -> treeSet.stream();
      case "iterate" -> Stream.iterate(0, i -> i < SIZE, i -> i + 1);
      case "lines" -> new BufferedReader(new StringReader(text)).lines().map(Integer::valueOf);
      default -> throw new IllegalArgumentException(source);
    };
  }

  @Benchmark
  public long sequential() {
    return stream().mapToLong(this::work).sum();
  }

  @Benchmark
  public long parallel() {
    return stream().parallel().mapToLong(this::work).sum();
  }

  private long work(int i) {
    Blackhole.consumeCPU(TOKENS);
    return i;
  }
}
