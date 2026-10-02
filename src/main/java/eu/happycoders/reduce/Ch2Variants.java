package eu.happycoders.reduce;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.Optional;
import java.util.OptionalInt;

public class Ch2Variants {

  static void main() {
    System.out.println("== reduce(identity, accumulator): total length of all titles");
    int totalLength = BOOKS.stream().map(book -> book.title().length()).reduce(0, Integer::sum);
    System.out.println(totalLength);

    System.out.println("== reduce(identity, accumulator) on an empty stream");
    int noLength =
        BOOKS.stream()
            .filter(book -> book.year() < 1800)
            .map(book -> book.title().length())
            .reduce(0, Integer::sum);
    System.out.println(noLength);

    System.out.println("== reduce(accumulator): the book with the longest title");
    Optional<Book> longest =
        BOOKS.stream().reduce((a, b) -> a.title().length() >= b.title().length() ? a : b);
    System.out.println(longest.map(Book::title));

    System.out.println("== reduce(accumulator) on an empty stream");
    Optional<Book> none =
        BOOKS.stream()
            .filter(book -> book.year() < 1800)
            .reduce((a, b) -> a.title().length() >= b.title().length() ? a : b);
    System.out.println(none.map(Book::title));

    System.out.println("== reduce(identity, accumulator, combiner): from Book to int");
    int totalLength3 =
        BOOKS.stream().reduce(0, (sum, book) -> sum + book.title().length(), Integer::sum);
    System.out.println(totalLength3);

    System.out.println("== IntStream.reduce(): no boxing");
    int totalLengthInt =
        BOOKS.stream().mapToInt(book -> book.title().length()).reduce(0, Integer::sum);
    System.out.println(totalLengthInt);

    System.out.println("== IntStream.reduce() without identity: OptionalInt");
    OptionalInt maxLength =
        BOOKS.stream().mapToInt(book -> book.title().length()).reduce(Math::max);
    System.out.println(maxLength);
  }
}
