package eu.happycoders.reduce;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Ch7Mistakes {

  static void main() {
    System.out.println("== Optional.get() on an empty result");
    try {
      Book book =
          BOOKS.stream()
              .filter(b -> b.year() < 1800)
              .reduce((a, b) -> a.year() <= b.year() ? a : b)
              .get();
      System.out.println(book);
    } catch (RuntimeException e) {
      System.out.println(e);
    }

    System.out.println("== Overflow: 13! does not fit into an int");
    System.out.println(IntStream.rangeClosed(1, 13).reduce(1, (a, b) -> a * b));
    System.out.println(LongStream.rangeClosed(1, 13).reduce(1, (a, b) -> a * b));

    System.out.println("== Math::multiplyExact throws instead");
    try {
      System.out.println(IntStream.rangeClosed(1, 13).reduce(1, Math::multiplyExact));
    } catch (ArithmeticException e) {
      System.out.println(e);
    }

    System.out.println("== Boxing: mapToInt() and sum() stay with int");
    int totalLength = BOOKS.stream().mapToInt(book -> book.title().length()).sum();
    System.out.println(totalLength);

    System.out.println("== 21! does not fit into a long either");
    System.out.println(LongStream.rangeClosed(1, 20).reduce(1, Math::multiplyExact));
    try {
      System.out.println(LongStream.rangeClosed(1, 21).reduce(1, Math::multiplyExact));
    } catch (ArithmeticException e) {
      System.out.println(e);
    }

    System.out.println("== BigInteger has no upper limit");
    BigInteger factorial25 =
        LongStream.rangeClosed(1, 25)
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);
    System.out.println(factorial25);
  }
}
