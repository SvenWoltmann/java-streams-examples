package eu.happycoders.optional;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Library;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class Ch6Streams {

  static void main() {
    System.out.println("== terminal operations that return an Optional");
    Optional<Book> oldest = BOOKS.stream().min(Comparator.comparingInt(Book::year));
    System.out.println(oldest.map(Book::title).orElseThrow());
    Optional<Book> anyAfter1900 = BOOKS.stream().filter(book -> book.year() > 1900).findAny();
    System.out.println(anyAfter1900);

    System.out.println("== Optional.stream()");
    List<String> wishList = List.of("Dracula", "Ulysses", "Moby-Dick", "Beloved");
    List<Book> available =
        wishList.stream().map(Library::findByTitle).flatMap(Optional::stream).toList();
    available.forEach(book -> System.out.println(book.title()));

    System.out.println("== OptionalInt and OptionalDouble");
    OptionalInt newestYear = BOOKS.stream().mapToInt(Book::year).max();
    System.out.println(newestYear);
    System.out.println(newestYear.getAsInt());
    OptionalDouble averageYear =
        BOOKS.stream().filter(book -> book.year() > 1900).mapToInt(Book::year).average();
    System.out.println(averageYear);
    System.out.println(averageYear.orElse(Double.NaN));
  }
}
