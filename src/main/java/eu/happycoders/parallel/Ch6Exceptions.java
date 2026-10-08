package eu.happycoders.parallel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class Ch6Exceptions {

  static void main() throws InterruptedException {
    System.out.println("== Ten exceptions among one million elements, five runs");
    for (int run = 0; run < 5; run++) {
      tenExceptions();
    }

    System.out.println("== One exception: does the throwing thread keep working? Five runs");
    for (int run = 0; run < 5; run++) {
      oneException();
    }
  }

  private static void tenExceptions() throws InterruptedException {
    AtomicInteger processed = new AtomicInteger();
    try {
      IntStream.range(0, 1_000_000)
          .parallel()
          .forEach(
              i -> {
                if (i % 100_000 == 7) throw new IllegalStateException("element " + i);
                processed.incrementAndGet();
              });
    } catch (IllegalStateException e) {
      int atCatch = processed.get();
      Thread.sleep(2000);
      System.out.println(
          e.getMessage() + " | at catch: " + atCatch + " | 2 s later: " + processed.get());
    }
  }

  private static void oneException() throws InterruptedException {
    AtomicReference<String> thrower = new AtomicReference<>();
    AtomicBoolean thrown = new AtomicBoolean();
    ConcurrentHashMap<String, AtomicInteger> afterThrow = new ConcurrentHashMap<>();
    try {
      IntStream.range(0, 1_000_000)
          .parallel()
          .forEach(
              i -> {
                if (i == 500_007) {
                  thrower.set(Thread.currentThread().getName());
                  thrown.set(true);
                  throw new IllegalStateException("element " + i);
                }
                if (thrown.get()) {
                  afterThrow
                      .computeIfAbsent(Thread.currentThread().getName(), _ -> new AtomicInteger())
                      .incrementAndGet();
                }
              });
    } catch (IllegalStateException _) {
      Thread.sleep(2000);
      AtomicInteger byThrower = afterThrow.get(thrower.get());
      System.out.println(
          "thrown in "
              + thrower.get()
              + " | threads working afterwards: "
              + afterThrow.size()
              + " | elements the throwing thread processed afterwards: "
              + (byThrower == null ? 0 : byThrower.get()));
    }
  }
}
