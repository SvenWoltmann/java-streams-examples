package eu.happycoders.parallel.benchmark;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Sequential vs. parallel over an ideally splittable source (IntStream.range) for a grid of element
 * counts (N) and work per element (Q, in Blackhole.consumeCPU tokens).
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(3)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class WorkPerElementBenchmark {

  @Param({"100", "1000", "10000", "100000", "1000000"})
  int size;

  @Param({"0", "10", "100", "1000"})
  int tokens;

  @Benchmark
  public long sequential() {
    return IntStream.range(0, size).mapToLong(this::work).sum();
  }

  @Benchmark
  public long parallel() {
    return IntStream.range(0, size).parallel().mapToLong(this::work).sum();
  }

  private long work(int i) {
    Blackhole.consumeCPU(tokens);
    return i;
  }
}
