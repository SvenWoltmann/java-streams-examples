package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Ch5Null {

  static void main() {
    Map<String, Integer> deathYearByAuthor =
        Map.of(
            "Jane Austen", 1817,
            "Mary Shelley", 1851,
            "Herman Melville", 1891,
            "Jules Verne", 1905);

    System.out.println("== null value, two arguments");
    try {
      Map<String, Integer> deathYearByTitle =
          BOOKS.stream().collect(toMap(Book::title, book -> deathYearByAuthor.get(book.author())));
      System.out.println(deathYearByTitle);
    } catch (NullPointerException e) {
      e.printStackTrace(System.out);
    }

    System.out.println("== null value, three arguments");
    try {
      Map<String, Integer> deathYearByTitle =
          BOOKS.stream()
              .collect(
                  toMap(
                      Book::title,
                      book -> deathYearByAuthor.get(book.author()),
                      (first, second) -> first));
      System.out.println(deathYearByTitle);
    } catch (NullPointerException e) {
      e.printStackTrace(System.out);
    }

    System.out.println("== filter()");
    Map<String, Integer> known =
        BOOKS.stream()
            .filter(book -> deathYearByAuthor.containsKey(book.author()))
            .collect(toMap(Book::title, book -> deathYearByAuthor.get(book.author())));
    System.out.println(known);

    System.out.println("== collect() with three arguments");
    Map<String, Integer> withNulls =
        BOOKS.stream()
            .collect(
                HashMap::new,
                (map, book) -> map.put(book.title(), deathYearByAuthor.get(book.author())),
                Map::putAll);
    System.out.println(withNulls);

    System.out.println("== null key in TreeMap");
    try {
      Map<Integer, String> sorted =
          BOOKS.stream()
              .collect(
                  toMap(
                      book -> deathYearByAuthor.get(book.author()),
                      Book::title,
                      (a, b) -> a,
                      TreeMap::new));
      System.out.println(sorted);
    } catch (NullPointerException e) {
      System.out.println(e + " @ " + e.getStackTrace()[0]);
    }
  }
}
