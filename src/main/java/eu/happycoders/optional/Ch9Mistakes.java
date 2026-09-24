package eu.happycoders.optional;

import static eu.happycoders.streams.Library.findByTitle;

import eu.happycoders.streams.Book;
import java.util.Optional;

public class Ch9Mistakes {

  /** Wrong: a method that returns an Optional must never return null. */
  static Optional<Book> findByTitleBroken(String title) {
    return title.isBlank() ? null : findByTitle(title);
  }

  static void main() {
    System.out.println("== isPresent() and get() instead of map() and orElse()");
    Optional<Book> book = findByTitle("Dracula");
    String title;
    if (book.isPresent()) {
      title = book.get().title();
    } else {
      title = "(unknown)";
    }
    System.out.println(title);
    System.out.println(findByTitle("Dracula").map(Book::title).orElse("(unknown)"));

    System.out.println("== get() on an empty Optional");
    try {
      System.out.println(findByTitle("Ulysses").get());
    } catch (RuntimeException e) {
      System.out.println(e);
    }

    System.out.println("== an Optional method that returns null");
    try {
      findByTitleBroken(" ").ifPresent(System.out::println);
    } catch (NullPointerException e) {
      System.out.println(e);
    }

    System.out.println("== Optional just to avoid an if");
    String author = "Mary Shelley";
    Optional.ofNullable(author).ifPresent(name -> System.out.println("Author: " + name));
    if (author != null) {
      System.out.println("Author: " + author);
    }
  }
}
