package eu.happycoders.optional;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;

/**
 * The two lookups of {@code Library}, written the way methods signaled "no result" before Java 8:
 * as {@code null}. They carry the same names as their {@code Optional} counterparts, because the
 * article's point is that the signature alone does not reveal the {@code null}.
 */
public class LibraryBeforeJava8 {

  private LibraryBeforeJava8() {}

  static Book findByTitle(String title) {
    for (Book book : BOOKS) {
      if (book.title().equals(title)) {
        return book;
      }
    }
    return null;
  }

  static Book nextBookBy(Book book) {
    Book next = null;
    for (Book otherBook : BOOKS) {
      if (otherBook.author().equals(book.author())
          && otherBook.year() > book.year()
          && (next == null || otherBook.year() < next.year())) {
        next = otherBook;
      }
    }
    return next;
  }

  static String nextTitleWithNullChecks(String title) {
    Book book = findByTitle(title);
    if (book != null) {
      Book next = nextBookBy(book);
      if (next != null) {
        return next.title();
      }
    }
    return "(none)";
  }
}
