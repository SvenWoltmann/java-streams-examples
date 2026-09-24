package eu.happycoders.optional;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Library;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Ch2Creating {

  static void main() {
    System.out.println("== of() and empty()");
    Optional<String> title = Optional.of("Dracula");
    Optional<String> noTitle = Optional.empty();
    System.out.println(title);
    System.out.println(noTitle);

    System.out.println("== ofNullable() for a value that may be null");
    Map<String, Book> byTitle = new HashMap<>();
    for (Book book : Library.BOOKS) {
      byTitle.put(book.title(), book);
    }
    System.out.println(Optional.ofNullable(byTitle.get("Dracula")));
    System.out.println(Optional.ofNullable(byTitle.get("Ulysses")));

    System.out.println("== of(null) throws immediately");
    try {
      Optional<Book> book = Optional.of(byTitle.get("Ulysses"));
      System.out.println(book);
    } catch (NullPointerException e) {
      System.out.println(e);
    }
  }
}
