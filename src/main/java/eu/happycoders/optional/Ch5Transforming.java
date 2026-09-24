package eu.happycoders.optional;

import static eu.happycoders.streams.Genre.ADVENTURE;
import static eu.happycoders.streams.Library.BOOKS;
import static eu.happycoders.streams.Library.findByTitle;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Library;
import java.util.Optional;

public class Ch5Transforming {

  static Optional<Book> findByTitleIgnoreCase(String title) {
    return BOOKS.stream().filter(book -> book.title().equalsIgnoreCase(title)).findFirst();
  }

  // The same two lookups as Library.findByTitle() and Library.nextBookBy(), written the way
  // methods returned "no result" before Java 8: as null.

  static Book findByTitleOrNull(String title) {
    for (Book book : BOOKS) {
      if (book.title().equals(title)) {
        return book;
      }
    }
    return null;
  }

  static Book nextBookByOrNull(Book book) {
    Book next = null;
    for (Book other : BOOKS) {
      if (other.author().equals(book.author())
          && other.year() > book.year()
          && (next == null || other.year() < next.year())) {
        next = other;
      }
    }
    return next;
  }

  static String nextTitleWithNullChecks(String title) {
    Book book = findByTitleOrNull(title);
    if (book != null) {
      Book next = nextBookByOrNull(book);
      if (next != null) {
        return next.title();
      }
    }
    return "(none)";
  }

  static String nextTitle(String title) {
    return findByTitle(title).flatMap(Library::nextBookBy).map(Book::title).orElse("(none)");
  }

  static void main() {
    System.out.println("== map()");
    Optional<Integer> year = findByTitle("Dracula").map(Book::year);
    System.out.println(year);
    System.out.println(findByTitle("Ulysses").map(Book::year));

    System.out.println("== map() with a function that returns an Optional");
    Optional<Optional<Book>> nested = findByTitle("Treasure Island").map(Library::nextBookBy);
    System.out.println(nested);

    System.out.println("== flatMap()");
    Optional<Book> next = findByTitle("Treasure Island").flatMap(Library::nextBookBy);
    System.out.println(next);
    System.out.println(findByTitle("Dracula").flatMap(Library::nextBookBy));

    System.out.println("== filter()");
    System.out.println(findByTitle("Treasure Island").filter(book -> book.genre() == ADVENTURE));
    System.out.println(findByTitle("Dracula").filter(book -> book.genre() == ADVENTURE));

    System.out.println("== or()");
    Optional<Book> book =
        findByTitle("treasure island").or(() -> findByTitleIgnoreCase("treasure island"));
    System.out.println(book);

    System.out.println("== nested null checks vs. a chain");
    for (String title : new String[] {"Treasure Island", "Dracula", "Ulysses"}) {
      System.out.println(title + ": " + nextTitleWithNullChecks(title) + " / " + nextTitle(title));
    }
  }
}
