package eu.happycoders.parallel;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Ch1Creating {

  static void main() {
    System.out.println("== Intro: books published before 1850, counted in parallel");
    long before1850 = BOOKS.parallelStream().filter(book -> book.year() < 1850).count();
    System.out.println(before1850);

    System.out.println("== parallelStream() on a collection (order varies)");
    BOOKS.parallelStream().map(Book::title).forEach(System.out::println);

    System.out.println("== parallel() on a LongStream");
    long sum = LongStream.rangeClosed(1, 1_000_000).parallel().sum();
    System.out.println(sum);

    System.out.println("== The same with an IntStream overflows");
    System.out.println(IntStream.rangeClosed(1, 1_000_000).parallel().sum());

    System.out.println("== The last parallel() or sequential() wins");
    boolean parallel =
        BOOKS.stream()
            .parallel()
            .filter(book -> book.year() > 1850)
            .sequential()
            .map(Book::title)
            .parallel()
            .isParallel();
    System.out.println(parallel);
    System.out.println(BOOKS.stream().parallel().sequential().isParallel());
  }
}
