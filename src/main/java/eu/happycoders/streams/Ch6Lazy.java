package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;

import java.util.List;
import java.util.stream.Stream;

public class Ch6Lazy {

  static void main() {
    System.out.println("== no terminal operation, nothing happens");
    BOOKS.stream()
        .filter(book -> book.year() > 1890)
        .peek(book -> System.out.println("filtered: " + book.title()));
    System.out.println("(end of pipeline without terminal operation)");

    System.out.println("== element by element");
    List<String> result =
        Stream.of("Dracula", "Frankenstein", "Kidnapped")
            .peek(title -> System.out.println("filter sees " + title))
            .filter(title -> title.length() > 8)
            .peek(title -> System.out.println("map sees    " + title))
            .map(String::toUpperCase)
            .toList();
    System.out.println(result);

    System.out.println("== short-circuit");
    String first =
        Stream.iterate(1, n -> n + 1)
            .peek(n -> System.out.println("checking " + n))
            .filter(n -> n % 7 == 0)
            .map(n -> "first multiple of 7: " + n)
            .findFirst()
            .orElseThrow();
    System.out.println(first);

    System.out.println("== stateful: sorted() needs all elements");
    List<String> sorted =
        Stream.of("Dracula", "Frankenstein", "Kidnapped")
            .peek(title -> System.out.println("before sorted: " + title))
            .sorted()
            .peek(title -> System.out.println("after sorted:  " + title))
            .toList();
    System.out.println(sorted);

    System.out.println("== a stream can be used only once");
    Stream<Book> books = BOOKS.stream();
    System.out.println(books.count());
    try {
      books.count();
    } catch (IllegalStateException e) {
      System.out.println("IllegalStateException: " + e.getMessage());
    }
  }
}
