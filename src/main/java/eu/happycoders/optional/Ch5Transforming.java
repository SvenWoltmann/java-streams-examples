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

  static String nextTitle(String title) {
    return findByTitle(title).flatMap(Library::nextBookBy).map(Book::title).orElse("(none)");
  }

  static void main() {
    System.out.println("== map()");
    Optional<Integer> draculaYear = findByTitle("Dracula").map(Book::year);
    System.out.println(draculaYear);
    Optional<Integer> ulyssesYear = findByTitle("Ulysses").map(Book::year);
    System.out.println(ulyssesYear);

    System.out.println("== map() with a function that returns an Optional");
    Optional<Optional<Book>> nestedAfterTreasureIsland =
        findByTitle("Treasure Island").map(Library::nextBookBy);
    System.out.println(nestedAfterTreasureIsland);
    Optional<Optional<Book>> nestedAfterDracula = findByTitle("Dracula").map(Library::nextBookBy);
    System.out.println(nestedAfterDracula);

    System.out.println("== flatMap()");
    Optional<Book> afterTreasureIsland =
        findByTitle("Treasure Island").flatMap(Library::nextBookBy);
    System.out.println(afterTreasureIsland);
    Optional<Book> afterDracula = findByTitle("Dracula").flatMap(Library::nextBookBy);
    System.out.println(afterDracula);

    System.out.println("== filter()");
    System.out.println(findByTitle("Treasure Island").filter(book -> book.genre() == ADVENTURE));
    System.out.println(findByTitle("Dracula").filter(book -> book.genre() == ADVENTURE));

    System.out.println("== or() vs. orElseGet()");
    Optional<Book> withOr =
        findByTitle("treasure island").or(() -> findByTitleIgnoreCase("treasure island"));
    System.out.println(withOr);
    Book withOrElseGet =
        findByTitle("treasure island")
            .orElseGet(() -> findByTitleIgnoreCase("treasure island").orElse(null));
    System.out.println(withOrElseGet);

    System.out.println("== nested null checks vs. a chain");
    for (String title : new String[] {"Treasure Island", "Dracula", "Ulysses"}) {
      System.out.println(
          title
              + ": "
              + LibraryBeforeJava8.nextTitleWithNullChecks(title)
              + " / "
              + nextTitle(title));
    }
  }
}
