package eu.happycoders.optional;

import static eu.happycoders.streams.Library.findByTitle;

import eu.happycoders.streams.Book;
import java.util.Optional;

public class Ch3GettingTheValue {

  static String defaultTitle() {
    System.out.println("  computing the default title");
    return "(unknown)";
  }

  static void main() {
    System.out.println("== orElse()");
    String title = findByTitle("Dracula").map(Book::title).orElse("(unknown)");
    System.out.println(title);
    String missing = findByTitle("Ulysses").map(Book::title).orElse("(unknown)");
    System.out.println(missing);

    System.out.println("== orElse() evaluates its argument even if a value is present");
    System.out.println(findByTitle("Dracula").map(Book::title).orElse(defaultTitle()));

    System.out.println("== orElseGet() calls the supplier only if the Optional is empty");
    System.out.println(findByTitle("Dracula").map(Book::title).orElseGet(() -> defaultTitle()));
    System.out.println(findByTitle("Ulysses").map(Book::title).orElseGet(() -> defaultTitle()));

    System.out.println("== orElseThrow() without an argument");
    try {
      Book book = findByTitle("Ulysses").orElseThrow();
      System.out.println(book);
    } catch (Exception e) {
      System.out.println(e);
    }

    System.out.println("== orElseThrow() with an exception supplier");
    try {
      Book book =
          findByTitle("Ulysses")
              .orElseThrow(() -> new IllegalArgumentException("Unknown title: Ulysses"));
      System.out.println(book);
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }

    System.out.println("== isPresent(), isEmpty(), and get()");
    Optional<Book> dracula = findByTitle("Dracula");
    if (dracula.isPresent()) {
      System.out.println(dracula.get().year());
    }
    System.out.println(findByTitle("Ulysses").isEmpty());
  }
}
