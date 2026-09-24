package eu.happycoders.optional;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class Ch7WhenToUse {

  /** A reservation with an optional note - the Optional field breaks serialization. */
  record Reservation(String title, Optional<String> note) implements Serializable {}

  /** Optional as a parameter: every caller has to wrap its argument. */
  static List<Book> booksBy(String author, Optional<Genre> genre) {
    return BOOKS.stream()
        .filter(book -> book.author().equals(author))
        .filter(book -> genre.map(g -> book.genre() == g).orElse(true))
        .toList();
  }

  /** The alternative: two overloads. */
  static List<Book> booksBy(String author) {
    return BOOKS.stream().filter(book -> book.author().equals(author)).toList();
  }

  static List<Book> booksBy(String author, Genre genre) {
    return booksBy(author).stream().filter(book -> book.genre() == genre).toList();
  }

  /** For a collection, "no result" is the empty collection, not an empty Optional. */
  static List<Book> booksPublishedIn(int year) {
    return BOOKS.stream().filter(book -> book.year() == year).toList();
  }

  static void main() throws IOException {
    System.out.println("== Optional as a parameter");
    System.out.println(booksBy("Jules Verne", Optional.empty()).size());
    System.out.println(booksBy("Jules Verne", Optional.of(Genre.ADVENTURE)).size());

    System.out.println("== overloads instead");
    System.out.println(booksBy("Jules Verne").size());
    System.out.println(booksBy("Jules Verne", Genre.ADVENTURE).size());

    System.out.println("== an empty list instead of an Optional<List>");
    System.out.println(booksPublishedIn(1865).size());
    System.out.println(booksPublishedIn(1900));

    System.out.println("== Optional is not Serializable");
    try (ObjectOutputStream out = new ObjectOutputStream(new ByteArrayOutputStream())) {
      out.writeObject(new Reservation("Dracula", Optional.of("second copy")));
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
