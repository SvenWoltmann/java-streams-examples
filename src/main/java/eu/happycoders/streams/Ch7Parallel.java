package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;

import java.util.stream.IntStream;

public class Ch7Parallel {

  static void main() {
    System.out.println("== parallel forEach (order varies)");
    BOOKS.parallelStream().map(Book::title).forEach(System.out::println);

    System.out.println("== parallel forEachOrdered");
    BOOKS.parallelStream().map(Book::title).forEachOrdered(System.out::println);

    System.out.println("== parallel reduce is still correct");
    long sum = IntStream.rangeClosed(1, 1_000_000).parallel().asLongStream().sum();
    System.out.println(sum);
    System.out.println(BOOKS.stream().parallel().isParallel());
  }
}
