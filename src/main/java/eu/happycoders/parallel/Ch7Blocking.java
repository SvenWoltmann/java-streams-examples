package eu.happycoders.parallel;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Ch7Blocking {

  static long computeMillis() {
    long start = System.nanoTime();
    LongStream.range(0, 400_000_000L).parallel().map(i -> i * i % 7).sum();
    return (System.nanoTime() - start) / 1_000_000;
  }

  static void blockingStream() {
    IntStream.range(0, 200).parallel().forEach(_ -> sleep(100));
  }

  static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  static void main() throws InterruptedException {
    for (int i = 0; i < 3; i++) computeMillis(); // warms up the JIT compiler
    System.out.println("alone: " + computeMillis() + " ms");

    Thread other = Thread.ofPlatform().start(Ch7Blocking::blockingStream);
    Thread.sleep(50);
    System.out.println("while the blocking stream runs: " + computeMillis() + " ms");
    other.join();

    long start = System.nanoTime();
    blockingStream();
    System.out.println("blocking stream alone: " + (System.nanoTime() - start) / 1_000_000 + " ms");
  }
}
