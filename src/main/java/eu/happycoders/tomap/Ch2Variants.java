package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import java.util.Map;
import java.util.TreeMap;

public class Ch2Variants {

  static void main() {
    System.out.println("== Two arguments: duplicate key");
    try {
      Map<String, Integer> yearByAuthor = BOOKS.stream().collect(toMap(Book::author, Book::year));
      System.out.println(yearByAuthor);
    } catch (IllegalStateException e) {
      System.out.println(e);
    }

    System.out.println("== Three arguments: the later book wins");
    Map<String, String> latestTitleByAuthor =
        BOOKS.stream().collect(toMap(Book::author, Book::title, (first, second) -> second));
    System.out.println(latestTitleByAuthor.get("Jules Verne"));

    System.out.println("== Three arguments: the first book wins");
    Map<String, String> firstTitleByAuthor =
        BOOKS.stream().collect(toMap(Book::author, Book::title, (first, second) -> first));
    System.out.println(firstTitleByAuthor.get("Jules Verne"));

    System.out.println("== Four arguments: TreeMap");
    Map<String, String> sorted =
        BOOKS.stream()
            .collect(toMap(Book::author, Book::title, (first, second) -> second, TreeMap::new));
    System.out.println(sorted);

    System.out.println("== Four arguments with a throwing merge function");
    try {
      Map<String, Integer> yearByAuthor =
          BOOKS.stream()
              .collect(
                  toMap(
                      Book::author,
                      Book::year,
                      (first, second) -> {
                        throw new IllegalStateException("Duplicate key");
                      },
                      TreeMap::new));
      System.out.println(yearByAuthor);
    } catch (IllegalStateException e) {
      System.out.println(e);
    }

    System.out.println("== collectingAndThen(): duplicate check with TreeMap");
    Map<String, Integer> yearByTitle =
        BOOKS.stream().collect(collectingAndThen(toMap(Book::title, Book::year), TreeMap::new));
    System.out.println(
        yearByTitle.getClass().getSimpleName() + " " + yearByTitle.keySet().iterator().next());
    try {
      BOOKS.stream().collect(collectingAndThen(toMap(Book::author, Book::year), TreeMap::new));
    } catch (IllegalStateException e) {
      System.out.println(e);
    }
  }
}
