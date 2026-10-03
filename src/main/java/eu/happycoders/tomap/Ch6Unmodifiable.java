package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableMap;

import eu.happycoders.streams.Book;
import java.util.Map;

public class Ch6Unmodifiable {

  static void main() {
    System.out.println("== toMap(): modifiable today");
    Map<String, Integer> yearByTitle = BOOKS.stream().collect(toMap(Book::title, Book::year));
    yearByTitle.put("The Invisible Man", 1897);
    System.out.println(yearByTitle.size());

    System.out.println("== toUnmodifiableMap()");
    Map<String, Integer> unmodifiable =
        BOOKS.stream().collect(toUnmodifiableMap(Book::title, Book::year));
    System.out.println(unmodifiable.getClass().getName());
    try {
      unmodifiable.put("The Invisible Man", 1897);
    } catch (UnsupportedOperationException e) {
      System.out.println(e + " @ " + e.getStackTrace()[0]);
    }
    System.out.println("order: " + unmodifiable.keySet().iterator().next());

    System.out.println("== toUnmodifiableMap() with merge function");
    Map<String, Integer> bookCountByAuthor =
        BOOKS.stream().collect(toUnmodifiableMap(Book::author, book -> 1, Integer::sum));
    System.out.println(bookCountByAuthor.get("H. G. Wells"));

    System.out.println("== toUnmodifiableMap() null key");
    try {
      Map<String, Integer> m =
          BOOKS.stream()
              .collect(
                  toUnmodifiableMap(
                      book -> book.year() < 1850 ? null : book.title(), Book::year, (a, b) -> a));
      System.out.println(m);
    } catch (NullPointerException e) {
      System.out.println(e + " @ " + e.getStackTrace()[0]);
    }
  }
}
