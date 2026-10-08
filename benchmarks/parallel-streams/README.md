# Parallel Streams Benchmarks

The JMH benchmarks behind the chapter "When Is a Parallel Stream Faster?" of the article [Java Parallel Streams](https://www.happycoders.eu/java/java-parallel-streams/) (German: [Parallele Streams in Java](https://www.happycoders.eu/de/java/java-parallel-streams/)).

This is a Maven project of its own, so that JMH stays out of the build of the examples one level up.

## The Benchmarks

Each benchmark compares a sequential with a parallel stream. The work per element comes from `Blackhole.consumeCPU()`, which runs a given number of work steps ("tokens").

| Benchmark | What it varies |
|---|---|
| `WorkPerElementBenchmark` | The element count (100 to 1,000,000) and the work per element (0 to 1,000 tokens), over `IntStream.range()` |
| `SourceBenchmark` | The source: `ArrayList`, `LinkedList`, `HashSet`, `TreeSet`, `Stream.iterate()`, `BufferedReader.lines()` – one million elements, 100 tokens each |
| `OrderBenchmark` | Operations that depend on the encounter order: `findFirst()` against `findAny()`, `limit()` with and without `unordered()`, `forEachOrdered()`, `sorted()`, `distinct()` |
| `CollectorBenchmark` | The merge cost of seven collectors, with no work and with 100 tokens per element |

Every value is the average of 3 forks × 5 measurement iterations of one second, after 5 warm-up iterations per fork.

## Running Them

```bash
mvn package
java -cp "target/parallel-streams-benchmarks-1.0-SNAPSHOT.jar:target/lib/*" \
    org.openjdk.jmh.Main eu.happycoders.parallel.benchmark.WorkPerElementBenchmark \
    -rf json -rff results.json
```

All four benchmarks together take about an hour on an 18-core machine. Close everything else while they run: a parallel stream measures the whole machine, not only the JVM.

## Results

The runs the article shows are in `results/<machine>/`, one JMH JSON file per benchmark and run date.
